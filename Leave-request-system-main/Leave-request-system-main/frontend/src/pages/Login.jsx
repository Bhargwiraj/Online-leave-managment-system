import React, { useState, useContext, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import './Auth.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  
  const { login, user } = useContext(AuthContext);
  const navigate = useNavigate();

  // Redirect if already logged in
  useEffect(() => {
    if (user) {
      navigate(user.role === 'ADMIN' ? '/admin/dashboard' : '/dashboard');
    }
  }, [user, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);
    
    if (!email || !password) {
      setError('Please fill in all fields');
      setIsLoading(false);
      return;
    }
    
    const result = await login(email, password);
    if (result.success) {
      navigate(result.user.role === 'ADMIN' ? '/admin/dashboard' : '/dashboard');
    } else {
      setError(result.message);
    }
    
    setIsLoading(false);
  };

  return (
    <div className="auth-container">
      <div className="glass-panel auth-card animate-fade-in">
        <div className="auth-logo">LRS</div>
        <h2>Welcome Back</h2>
        <p>Log in to your account to continue</p>
        
        {error && <div className="error-msg">{error}</div>}
        
        <form className="auth-form" onSubmit={handleSubmit}>
          <div className="input-group">
            <label className="input-label" htmlFor="email">Email</label>
            <input 
              type="email" 
              id="email" 
              className="input-field" 
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              autoComplete="email"
            />
          </div>
          
          <div className="input-group">
            <label className="input-label" htmlFor="password">Password</label>
            <input 
              type="password" 
              id="password" 
              className="input-field" 
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              autoComplete="current-password"
            />
          </div>
          
          <button 
            type="submit" 
            className="btn btn-primary auth-btn"
            disabled={isLoading}
          >
            {isLoading ? 'Logging in...' : 'Log In'}
          </button>
        </form>
        
        <div className="auth-footer">
          Don't have an account? <Link to="/register">Sign up here</Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
