import React, { createContext, useContext, useEffect, useState } from 'react';
import Config from '../config/Config';
import axios from 'axios';

const AuthContext = createContext();

const setStorageToken = (token) => {
  if (!!!token) {
    return;
  }

  localStorage.setItem('user_token', token);
};

const getStorageToken = () => {
  return localStorage.getItem('user_token');
};

const unsetStorageToken = () => {
  localStorage.removeItem('user_token');
};

const setStorageUserData = (userData) => {
  if (!!!userData) {
    return;
  }

  localStorage.setItem('user_data', JSON.stringify(userData));
};

const getStorageUserData = () => {
  const u = localStorage.getItem('user_data');

  if (!!!u) {
    return null;
  }

  return JSON.parse(u);
};

const unsetStorageUserData = () => {
  localStorage.removeItem('user_data');
};

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(getStorageToken() || null);
  const [userData, setUserData] = useState(getStorageUserData() || null);

  const getToken = () => {
    return token;
  };

  const unsetToken = () => {
    setToken(null);
  };

  const getUserData = () => {
    return userData;
  };

  const unsetUserData = () => {
    setUserData(null);
  };

  /* --- */

  const isLoggedIn = () => {
    return !!token && !!userData;
  };

  const getTokenHeader = () => {
    if (!isLoggedIn()) {
      return null;
    }

    return {
      Authorization: `Bearer ${token}`
    };
  };

  const getRole = () => {
    if (!isLoggedIn()) {
      return null;
    }

    return userData.role;
  };

  /* --- */

  const setUser = (data) => {
    console.log({ data })
    setToken(data.token);
    setStorageToken(data.token);

    setUserData(data.userResponse);
    setStorageUserData(data.userResponse);
    console.log("labas", getUserData());
    console.log("labanakt", getStorageUserData());
  };

  const login = async (email, password, callback = {}) => {
    const callbackThen = callback.then;

    callback.then = (response) => {
      setUser(response.data);
      console.log(response.data);

      if (!!callbackThen) {
        callbackThen(response);
      }
    };

    const url = Config.apiDomain + Config.endpoints.auth.login;

    const body = {
      email,
      password
    };


    axios.post(url, body).then(callback.then).catch(callback.catch).finally(callback.finally);
  };

  const logout = () => {
    unsetToken();
    unsetStorageToken();

    unsetUserData();
    unsetStorageUserData();
  };

  return (
    <AuthContext.Provider
      value={{
        getToken,
        getTokenHeader,
        isLoggedIn,
        getUserData,
        getRole,
        setUser,
        login,
        logout
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
