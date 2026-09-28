import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import { Users, FileCheck, ArrowRight } from 'lucide-react';
import './Dashboard.css';

const AdminDashboard = () => {
  const [stats, setStats] = useState({ total: 0, pending: 0, approved: 0, rejected: 0 });
  const [pendingLeaves, setPendingLeaves] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const statsRes = await api.get('/admin/dashboard');
        setStats(statsRes.data.data.stats);

        const leavesRes = await api.get('/admin/leaves/pending');
        // Get only up to 5 pending leaves
        setPendingLeaves(leavesRes.data.data.slice(0, 5));
        setLoading(false);
      } catch (error) {
        console.error('Error fetching admin dashboard data:', error);
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  if (loading) {
    return <div className="loading-spinner">Loading dashboard...</div>;
  }

  return (
    <div className="animate-fade-in">
      <div className="section-header">
        <h1 className="section-title">Admin Dashboard</h1>
      </div>

      <div className="dashboard-grid">
        <div className="glass-card stat-card stat-total">
          <div className="stat-title">Total Requests</div>
          <div className="stat-value">{stats.total}</div>
        </div>
        <div className="glass-card stat-card stat-pending">
          <div className="stat-title">Pending Action</div>
          <div className="stat-value">{stats.pending}</div>
        </div>
        <div className="glass-card stat-card stat-approved">
          <div className="stat-title">Approved</div>
          <div className="stat-value">{stats.approved}</div>
        </div>
        <div className="glass-card stat-card stat-rejected">
          <div className="stat-title">Rejected</div>
          <div className="stat-value">{stats.rejected}</div>
        </div>
      </div>

      <div className="dashboard-section">
        <div className="section-header">
          <h2 className="section-title">Leaves Requiring Attention</h2>
          <Link to="/admin/manage-leaves" className="btn btn-secondary" style={{ padding: '0.5rem 1rem', fontSize: '0.85rem' }}>
            Manage All <ArrowRight size={16} />
          </Link>
        </div>

        <div className="glass-card table-container">
          {pendingLeaves.length > 0 ? (
            <table className="data-table">
              <thead>
                <tr>
                  <th>Employee</th>
                  <th>Type</th>
                  <th>Duration</th>
                  <th>Reason</th>
                  <th>Applied On</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {pendingLeaves.map((leave) => (
                  <tr key={leave.id}>
                    <td>
                      <div style={{ fontWeight: 500 }}>{leave.userName}</div>
                      <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>{leave.userEmail}</div>
                    </td>
                    <td>{leave.leaveType}</td>
                    <td>{leave.startDate} to {leave.endDate}</td>
                    <td style={{ maxWidth: '200px', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
                      {leave.reason}
                    </td>
                    <td>{leave.appliedDate}</td>
                    <td>
                      <Link to="/admin/manage-leaves" className="btn btn-primary" style={{ padding: '0.25rem 0.75rem', fontSize: '0.75rem' }}>
                        Review
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <div className="empty-state" style={{ padding: '2rem' }}>
              <FileCheck size={48} style={{ opacity: 0.5, color: 'var(--success)' }} />
              <p>All caught up! There are no pending leave requests right now.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
