/**
 * Authentication context provider for managing user authentication state.
 *
 * <p>Provides authentication functionality including login, register, and logout operations.
 * Manages user state and authentication tokens stored in localStorage.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */

import { createContext, useContext, useState, useEffect } from 'react'
import { registerUser, loginUser, logoutUser } from '../services/api'

const AuthContext = createContext(null)

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
}

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    // Check if user is already logged in
    const storedUser = localStorage.getItem('user')
    const storedToken = localStorage.getItem('token')
    
    if (storedUser && storedToken) {
      try {
        setUser(JSON.parse(storedUser))
      } catch (error) {
        console.error('Error parsing stored user:', error)
        localStorage.removeItem('user')
        localStorage.removeItem('token')
      }
    }
    setLoading(false)
  }, [])

  const login = async (email, password) => {
    try {
      const response = await loginUser({ email, password })
      const { user: userData, token } = response
      
      localStorage.setItem('token', token)
      localStorage.setItem('user', JSON.stringify(userData))
      setUser(userData)
      
      return { success: true }
    } catch (error) {
      const errorMessage = error.response?.data?.error || 'Login failed. Please try again.'
      return { success: false, error: errorMessage }
    }
  }

  const register = async (name, email, password) => {
    try {
      await registerUser({ name, email, password })
      // After registration, automatically log in
      return await login(email, password)
    } catch (error) {
      const errorMessage = error.response?.data?.error || 'Registration failed. Please try again.'
      return { success: false, error: errorMessage }
    }
  }

  const logout = async () => {
    try {
      await logoutUser()
    } catch (error) {
      console.error('Logout error:', error)
    } finally {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      setUser(null)
    }
  }

  const value = {
    user,
    login,
    register,
    logout,
    loading,
    isAuthenticated: !!user,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

