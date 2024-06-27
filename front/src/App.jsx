import './App.css';
import { Route, Routes } from 'react-router-dom';
import ErrorPage from './components/errorpage/ErrorPage.jsx';
import NavBar from './components/NavBar.jsx';
import HomePage from './components/homepage/HomePage.jsx';
import { AuthProvider } from './components/authentication/AuthContext.jsx';
import LoginPage from './components/loginpage/LoginPage.jsx';
import CategoryPage from './components/categorypage/CategoryPage.jsx';
import AdvertPage from './components/advertmanagepage/AdvertPage.jsx';
import AdvertUpdate from './components/advertmanagepage/AdvertUpdate.jsx';
import AdvertDetailsPage from './components/advertdetailspage/AdvertDetailsPage.jsx';
import UserAdvertPage from './components/useradvertpage/UserAdvertPage.jsx';

function App() {
  return (
    <>
      <NavBar />
      <AuthProvider>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/category" element={<CategoryPage />} />
          <Route path="/advert" element={<AdvertPage />} />
          <Route path="/update/:id" element={<AdvertUpdate />} />
          <Route path="/details/:id" element={<AdvertDetailsPage />} />
          <Route path="*" element={<ErrorPage />} />
          <Route path="/advertsforusers" element={<UserAdvertPage />} />
        </Routes>
      </AuthProvider>
    </>
  );
}

export default App;
