/**
 * API service layer for communicating with the backend Spring Boot REST API.
 *
 * <p>Configures Axios instance with base URL, JSON headers, and interceptors for
 * authentication tokens. Provides API functions for user registration, login, and logout.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */

import axios from 'axios'

// Base URL for the Spring Boot backend API
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

// Create Axios instance with base configuration
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to attach JWT token from localStorage
api.interceptors.request.use(
  (config) => {
    // Get token from localStorage
    const token = localStorage.getItem('token')
    
    // Attach Authorization header if token exists
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor to handle errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Handle 401 Unauthorized - clear token and redirect to login
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

/**
 * Registers a new user account.
 *
 * @param {Object} userData - User registration data containing name, email, and password
 * @returns {Promise<Object>} Response data with user information
 */
export const registerUser = async (userData) => {
  const response = await api.post('/register', userData)
  return response.data
}

/**
 * Authenticates a user and returns token and user data.
 *
 * @param {Object} credentials - Login credentials containing email and password
 * @returns {Promise<Object>} Response data with token, user information, and message
 */
export const loginUser = async (credentials) => {
  const response = await api.post('/login', credentials)
  return response.data
}

/**
 * Logs out the current user and invalidates the session token.
 *
 * @returns {Promise<Object>} Response data with logout confirmation message
 */
export const logoutUser = async () => {
  const response = await api.post('/logout')
  return response.data
}

// Export the configured axios instance for direct use if needed
export default api
