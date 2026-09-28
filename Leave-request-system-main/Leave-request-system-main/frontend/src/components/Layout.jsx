import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';
import Sidebar from './Sidebar';

const Layout = ({ isAdmin = false }) => {
  return (
    <div className="app-container">
      <Sidebar isAdmin={isAdmin} />
      <div className="main-content">
        <Navbar />
        <main className="page-content">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default Layout;
