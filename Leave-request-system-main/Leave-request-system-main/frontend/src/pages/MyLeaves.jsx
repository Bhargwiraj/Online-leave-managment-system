import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import { FileText, XCircle, AlertCircle } from 'lucide-react';

const MyLeaves = () => {
  const [leaves, setLeaves] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchLeaves();
  }, []);

  const fetchLeaves = async () => {
    try {
      const response = await api.get('/leaves/my-leaves');
      setLeaves(response.data.data);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch leave history.');
      setLoading(false);
    }
  };

  const handleCancel = async (id) => {
    if (!window.confirm('Are you sure you want to cancel this leave request?')) return;
    
    try {
      await api.put(`/leaves/${id}/cancel`);
      // Update UI by changing status
      setLeaves(leaves.map(leave => 
        leave.id === id ? { ...leave, status: 'CANCELLED' } : leave
      ));
    } catch (err) {
      alert(err.response?.data?.message || 'Failed to cancel leave');
    }
  };

  if (loading) {
    return <div className="loading-spinner">Loading your leaves...</div>;
  }

  return (
    <div className="animate-fade-in">
      <div className="section-header">
        <h1 className="section-title">My Leave History</h1>
        <Link to="/apply-leave" className="btn btn-primary">
          <FileText size={18} /> Apply for Leave
        </Link>
      </div>

      {error && <div className="error-msg"><AlertCircle size={18} style={{display: 'inline', verticalAlign: 'text-bottom'}}/> {error}</div>}

      <div className="glass-card table-container">
        {leaves.length > 0 ? (
          <table className="data-table">
            <thead>
              <tr>
                <th>Type</th>
                <th>Dates</th>
                <th>Reason</th>
                <th>Applied On</th>
                <th>Status</th>
                <th>Admin Remark</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {leaves.map((leave) => (
                <tr key={leave.id}>
                  <td>{leave.leaveType}</td>
                  <td style={{ whiteSpace: 'nowrap' }}>{leave.startDate} <br/>to<br/> {leave.endDate}</td>
                  <td>{leave.reason}</td>
                  <td>{leave.appliedDate}</td>
                  <td>
                    <span className={`badge badge-${leave.status.toLowerCase()}`}>
                      {leave.status}
                    </span>
                  </td>
                  <td>{leave.adminRemark || '-'}</td>
                  <td>
                    {leave.status === 'PENDING' ? (
                      <button 
                        className="btn btn-secondary btn-icon" 
                        style={{ color: 'var(--danger)', borderColor: 'var(--danger-bg)' }}
                        onClick={() => handleCancel(leave.id)}
                        title="Cancel Request"
                      >
                        <XCircle size={18} />
                      </button>
                    ) : (
                      <span style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>No actions</span>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <div className="empty-state">
            <FileText size={48} style={{ opacity: 0.5 }} />
            <p>You haven't applied for any leaves yet.</p>
            <Link to="/apply-leave" className="btn btn-primary">Apply Now</Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default MyLeaves;
