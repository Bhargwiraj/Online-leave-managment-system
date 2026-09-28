import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import { FileText, Check, X, AlertCircle } from 'lucide-react';
import './Dashboard.css';

const ManageLeaves = () => {
  const [leaves, setLeaves] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  
  // Modal state
  const [modalOpen, setModalOpen] = useState(false);
  const [activeLeave, setActiveLeave] = useState(null);
  const [actionType, setActionType] = useState(''); // 'approve' or 'reject'
  const [remark, setRemark] = useState('');
  const [actionLoading, setActionLoading] = useState(false);

  useEffect(() => {
    fetchAllLeaves();
  }, []);

  const fetchAllLeaves = async () => {
    try {
      const response = await api.get('/admin/leaves');
      setLeaves(response.data.data);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch leave requests.');
      setLoading(false);
    }
  };

  const openActionModal = (leave, action) => {
    setActiveLeave(leave);
    setActionType(action);
    setRemark('');
    setModalOpen(true);
  };

  const closeActionModal = () => {
    setModalOpen(false);
    setActiveLeave(null);
    setRemark('');
  };

  const handleActionSubmit = async (e) => {
    e.preventDefault();
    setActionLoading(true);
    
    try {
      const endpoint = `/admin/leaves/${activeLeave.id}/${actionType}`;
      await api.put(endpoint, { remark });
      
      // Update UI
      setLeaves(leaves.map(leave => 
        leave.id === activeLeave.id 
          ? { ...leave, status: actionType === 'approve' ? 'APPROVED' : 'REJECTED', adminRemark: remark } 
          : leave
      ));
      
      closeActionModal();
    } catch (err) {
      alert(err.response?.data?.message || `Failed to ${actionType} leave`);
    } finally {
      setActionLoading(false);
    }
  };

  if (loading) {
    return <div className="loading-spinner">Loading leave requests...</div>;
  }

  return (
    <div className="animate-fade-in position-relative">
      <div className="section-header">
        <h1 className="section-title">Manage Leave Requests</h1>
      </div>

      {error && <div className="error-msg"><AlertCircle size={18} style={{display: 'inline', verticalAlign: 'text-bottom'}}/> {error}</div>}

      <div className="glass-card table-container">
        {leaves.length > 0 ? (
          <table className="data-table">
            <thead>
              <tr>
                <th>Employee</th>
                <th>Type</th>
                <th>Dates</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Remark</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {leaves.map((leave) => (
                <tr key={leave.id}>
                  <td>
                    <div style={{ fontWeight: 500 }}>{leave.userName}</div>
                    <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>{leave.userEmail}</div>
                  </td>
                  <td>{leave.leaveType}</td>
                  <td style={{ whiteSpace: 'nowrap' }}>{leave.startDate} <br/>to<br/> {leave.endDate}</td>
                  <td style={{ maxWidth: '200px', overflow: 'hidden', textOverflow: 'ellipsis' }}>
                    {leave.reason}
                  </td>
                  <td>
                    <span className={`badge badge-${leave.status.toLowerCase()}`}>
                      {leave.status}
                    </span>
                  </td>
                  <td style={{ maxWidth: '150px', overflow: 'hidden', textOverflow: 'ellipsis' }}>
                    {leave.adminRemark || '-'}
                  </td>
                  <td>
                    {leave.status === 'PENDING' ? (
                      <div style={{ display: 'flex', gap: '0.5rem' }}>
                        <button 
                          className="btn btn-secondary btn-icon" 
                          style={{ color: 'var(--success)', borderColor: 'var(--success-bg)' }}
                          onClick={() => openActionModal(leave, 'approve')}
                          title="Approve"
                        >
                          <Check size={18} />
                        </button>
                        <button 
                          className="btn btn-secondary btn-icon" 
                          style={{ color: 'var(--danger)', borderColor: 'var(--danger-bg)' }}
                          onClick={() => openActionModal(leave, 'reject')}
                          title="Reject"
                        >
                          <X size={18} />
                        </button>
                      </div>
                    ) : (
                      <span style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>Processed</span>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <div className="empty-state">
            <FileText size={48} style={{ opacity: 0.5 }} />
            <p>No leave requests found in the system.</p>
          </div>
        )}
      </div>

      {/* Action Modal */}
      {modalOpen && (
        <div style={{
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: 'rgba(0, 0, 0, 0.7)',
          backdropFilter: 'blur(4px)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          zIndex: 1000
        }}>
          <div className="glass-card animate-fade-in" style={{
            width: '100%',
            maxWidth: '500px',
            padding: '2rem',
            background: 'var(--bg-dark)',
            border: '1px solid var(--border-color)'
          }}>
            <h2 style={{ marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem', color: actionType === 'approve' ? 'var(--success)' : 'var(--danger)' }}>
              {actionType === 'approve' ? <Check size={24} /> : <X size={24} />}
              {actionType === 'approve' ? 'Approve Leave Request' : 'Reject Leave Request'}
            </h2>
            
            <div style={{ marginBottom: '1.5rem', padding: '1rem', background: 'rgba(255,255,255,0.03)', borderRadius: '8px' }}>
              <div style={{ marginBottom: '0.5rem' }}><strong>Employee:</strong> {activeLeave?.userName}</div>
              <div style={{ marginBottom: '0.5rem' }}><strong>Dates:</strong> {activeLeave?.startDate} to {activeLeave?.endDate}</div>
              <div><strong>Reason:</strong> {activeLeave?.reason}</div>
            </div>

            <form onSubmit={handleActionSubmit}>
              <div className="input-group">
                <label className="input-label" htmlFor="remark">Add a remark (optional)</label>
                <textarea 
                  id="remark" 
                  className="input-field" 
                  style={{ minHeight: '80px' }}
                  placeholder={`Reason for ${actionType}ing this request...`}
                  value={remark}
                  onChange={(e) => setRemark(e.target.value)}
                ></textarea>
              </div>

              <div style={{ display: 'flex', gap: '1rem', justifyContent: 'flex-end', marginTop: '2rem' }}>
                <button type="button" className="btn btn-secondary" onClick={closeActionModal} disabled={actionLoading}>
                  Cancel
                </button>
                <button 
                  type="submit" 
                  className="btn" 
                  style={{ 
                    background: actionType === 'approve' ? 'var(--success)' : 'var(--danger)',
                    color: 'white'
                  }}
                  disabled={actionLoading}
                >
                  {actionLoading ? 'Processing...' : `Confirm ${actionType === 'approve' ? 'Approval' : 'Rejection'}`}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default ManageLeaves;
