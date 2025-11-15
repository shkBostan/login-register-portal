/**
 * Application entry point for the Login Register Portal frontend.
 *
 * <p>Renders the root React application component and initializes the application
 * with React StrictMode for development best practices.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */

import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)

