import './App.css';
import { Route, Routes } from 'react-router-dom';
import ErrorPage from './components/errorpage/ErrorPage.jsx';
import NavBar from './components/NavBar.jsx';
import HomePage from './components/homepage/HomePage.jsx';
import { AuthProvider } from './components/authentication/AuthContext.jsx';
import LoginPage from './components/loginpage/LoginPage.jsx';
import CategoryPage from './components/categorypage/CategoryPage.jsx';

function App() {
  return (
    <>
      <NavBar />
      <AuthProvider>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/category" element={<CategoryPage />} />
          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </AuthProvider>
    </>
  );
}

export default App;
