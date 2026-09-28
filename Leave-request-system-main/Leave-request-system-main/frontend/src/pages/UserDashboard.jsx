import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import { Clock, CheckCircle, XCircle, FileText, ArrowRight } from 'lucide-react';
import './Dashboard.css';

const UserDashboard = () => {
  const [stats, setStats] = useState({ total: 0, pending: 0, approved: 0, rejected: 0 });
  const [recentLeaves, setRecentLeaves] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const statsRes = await api.get('/leaves/dashboard');
        setStats(statsRes.data.data.stats);

        const leavesRes = await api.get('/leaves/my-leaves');
        // Get only the 5 most recent leaves
        setRecentLeaves(leavesRes.data.data.slice(0, 5));
        setLoading(false);
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
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
        <h1 className="section-title">Dashboard Overview</h1>
        <Link to="/apply-leave" className="btn btn-primary">
          <FileText size={18} /> Apply for Leave
        </Link>
      </div>

      <div className="dashboard-grid">
        <div className="glass-card stat-card stat-total">
          <div className="stat-title">Total Applied</div>
          <div className="stat-value">{stats.total}</div>
        </div>
        <div className="glass-card stat-card stat-pending">
          <div className="stat-title">Pending</div>
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
          <h2 className="section-title">Recent Leave Requests</h2>
          <Link to="/my-leaves" className="btn btn-secondary" style={{ padding: '0.5rem 1rem', fontSize: '0.85rem' }}>
            View All <ArrowRight size={16} />
          </Link>
        </div>

        <div className="glass-card table-container">
          {recentLeaves.length > 0 ? (
            <table className="data-table">
              <thead>
                <tr>
                  <th>Type</th>
                  <th>Duration</th>
                  <th>Reason</th>
                  <th>Applied On</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {recentLeaves.map((leave) => (
                  <tr key={leave.id}>
                    <td>{leave.leaveType}</td>
                    <td>{leave.startDate} to {leave.endDate}</td>
                    <td style={{ maxWidth: '200px', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
                      {leave.reason}
                    </td>
                    <td>{leave.appliedDate}</td>
                    <td>
                      <span className={`badge badge-${leave.status.toLowerCase()}`}>
                        {leave.status}
                      </span>
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
    </div>
  );
};

export default UserDashboard;
