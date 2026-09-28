import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, CalendarPlus, List, FileCheck } from 'lucide-react';
import './Sidebar.css';

const Sidebar = ({ isAdmin }) => {
  return (
    <aside className="sidebar glass-panel">
      <div className="sidebar-header">
        <div className="logo-icon">LRS</div>
      </div>
      
      <nav className="sidebar-nav">
        {!isAdmin ? (
          <>
            <NavLink to="/dashboard" className={({isActive}) => isActive ? 'nav-item active' : 'nav-item'}>
              <LayoutDashboard size={20} />
              <span>Dashboard</span>
            </NavLink>
            <NavLink to="/apply-leave" className={({isActive}) => isActive ? 'nav-item active' : 'nav-item'}>
              <CalendarPlus size={20} />
              <span>Apply Leave</span>
            </NavLink>
            <NavLink to="/my-leaves" className={({isActive}) => isActive ? 'nav-item active' : 'nav-item'}>
              <List size={20} />
              <span>My Leaves</span>
            </NavLink>
          </>
        ) : (
          <>
            <NavLink to="/admin/dashboard" className={({isActive}) => isActive ? 'nav-item active' : 'nav-item'}>
              <LayoutDashboard size={20} />
              <span>Dashboard</span>
            </NavLink>
            <NavLink to="/admin/manage-leaves" className={({isActive}) => isActive ? 'nav-item active' : 'nav-item'}>
              <FileCheck size={20} />
              <span>Manage Leaves</span>
            </NavLink>
          </>
        )}
      </nav>
    </aside>
  );
};

export default Sidebar;
