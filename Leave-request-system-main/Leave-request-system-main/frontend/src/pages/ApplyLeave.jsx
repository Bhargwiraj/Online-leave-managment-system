import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { Send, AlertCircle } from 'lucide-react';
import './Dashboard.css';

const ApplyLeave = () => {
  const [formData, setFormData] = useState({
    leaveType: 'CASUAL',
    startDate: '',
    endDate: '',
    reason: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);
  
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.id]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    
    if (!formData.startDate || !formData.endDate || !formData.reason) {
      setError('Please fill in all fields');
      return;
    }
    
    const start = new Date(formData.startDate);
    const end = new Date(formData.endDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    if (start < today) {
      setError('Start date cannot be in the past');
      return;
    }

    if (start > end) {
      setError('Start date cannot be after end date');
      return;
    }

    setLoading(true);
    try {
      await api.post('/leaves/apply', formData);
      setSuccess(true);
      setTimeout(() => {
        navigate('/my-leaves');
      }, 2000);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to submit leave request');
    }
    setLoading(false);
  };

  return (
    <div className="animate-fade-in">
      <div className="section-header">
        <h1 className="section-title">Apply for Leave</h1>
      </div>

      <div className="glass-card form-container">
        {error && <div className="error-msg" style={{display: 'flex', alignItems: 'center', gap: '0.5rem'}}><AlertCircle size={18} /> {error}</div>}
        
        {success ? (
          <div className="empty-state" style={{ padding: '2rem' }}>
            <div style={{ color: 'var(--success)', marginBottom: '1rem' }}>
              <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                <polyline points="22 4 12 14.01 9 11.01"></polyline>
              </svg>
            </div>
            <h3 style={{ color: 'white', marginBottom: '0.5rem' }}>Leave Request Submitted!</h3>
            <p>Your request has been successfully submitted and is pending approval.</p>
            <p style={{ fontSize: '0.85rem' }}>Redirecting you to your leaves...</p>
          </div>
        ) : (
          <form onSubmit={handleSubmit}>
            <div className="input-group">
              <label className="input-label" htmlFor="leaveType">Leave Type</label>
              <select 
                id="leaveType" 
                className="input-field" 
                value={formData.leaveType}
                onChange={handleChange}
              >
                <option value="CASUAL">Casual Leave</option>
                <option value="SICK">Sick Leave</option>
                <option value="PERSONAL">Personal Leave</option>
                <option value="EMERGENCY">Emergency Leave</option>
              </select>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label className="input-label" htmlFor="startDate">Start Date</label>
                <input 
                  type="date" 
                  id="startDate" 
                  className="input-field" 
                  value={formData.startDate}
                  onChange={handleChange}
                  min={new Date().toISOString().split('T')[0]}
                />
              </div>
              
              <div className="input-group">
                <label className="input-label" htmlFor="endDate">End Date</label>
                <input 
                  type="date" 
                  id="endDate" 
                  className="input-field" 
                  value={formData.endDate}
                  onChange={handleChange}
                  min={formData.startDate || new Date().toISOString().split('T')[0]}
                />
              </div>
            </div>

            <div className="input-group">
              <label className="input-label" htmlFor="reason">Reason for Leave</label>
              <textarea 
                id="reason" 
                className="input-field" 
                placeholder="Please provide a detailed reason..."
                value={formData.reason}
                onChange={handleChange}
              ></textarea>
            </div>

            <button type="submit" className="btn btn-primary" style={{ width: '100%' }} disabled={loading}>
              {loading ? 'Submitting...' : <><Send size={18} /> Submit Application</>}
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default ApplyLeave;
