/**
 * Dashboard page component displaying user information and welcome message.
 *
 * <p>Shows a welcome message with the user's name and displays user information
 * including name, email, and user ID. Only accessible to authenticated users.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */

import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const Dashboard = () => {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = async () => {
    await logout()
    navigate('/login')
  }

  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-12 bg-gradient-to-br from-indigo-500 via-purple-500 to-pink-500">
      <div className="w-full max-w-4xl">
        <div className="bg-white rounded-2xl shadow-2xl p-8 md:p-10 animate-fade-in">
          <div className="flex justify-between items-center mb-8 pb-6 border-b-2 border-gray-200">
            <h1 className="text-3xl md:text-4xl font-bold text-gray-800">
              Welcome to Your Dashboard
            </h1>
            <button
              onClick={handleLogout}
              className="px-6 py-2 bg-red-500 text-white font-semibold rounded-lg hover:bg-red-600 transform hover:-translate-y-0.5 transition-all duration-200 shadow-lg hover:shadow-xl"
            >
              Logout
            </button>
          </div>

          <div className="space-y-8">
            <div className="text-center">
              <h2 className="text-2xl md:text-3xl font-semibold text-gray-800 mb-3">
                Hello, {user?.name || 'User'}!
              </h2>
              <p className="text-gray-600 text-lg">
                You have successfully logged into the portal.
              </p>
            </div>

            <div className="bg-gradient-to-br from-gray-50 to-gray-100 rounded-xl p-6 md:p-8">
              <h3 className="text-xl font-semibold text-gray-800 mb-6">
                Your Information
              </h3>
              <div className="space-y-4">
                <div className="flex flex-col sm:flex-row sm:justify-between sm:items-center py-3 border-b border-gray-300 last:border-b-0">
                  <span className="font-semibold text-gray-700 text-sm mb-2 sm:mb-0">
                    Name:
                  </span>
                  <span className="text-gray-800 font-medium text-base">
                    {user?.name || 'N/A'}
                  </span>
                </div>
                <div className="flex flex-col sm:flex-row sm:justify-between sm:items-center py-3 border-b border-gray-300 last:border-b-0">
                  <span className="font-semibold text-gray-700 text-sm mb-2 sm:mb-0">
                    Email:
                  </span>
                  <span className="text-gray-800 font-medium text-base">
                    {user?.email || 'N/A'}
                  </span>
                </div>
                <div className="flex flex-col sm:flex-row sm:justify-between sm:items-center py-3 border-b border-gray-300 last:border-b-0">
                  <span className="font-semibold text-gray-700 text-sm mb-2 sm:mb-0">
                    User ID:
                  </span>
                  <span className="text-gray-800 font-medium text-base">
                    {user?.id || 'N/A'}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Dashboard
