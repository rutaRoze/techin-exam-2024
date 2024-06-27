import { useState } from 'react';
import { Link } from 'react-router-dom';

function NavBar() {
  const [isNavCollapsed, setIsNavCollapsed] = useState(true);

  const handleNavCollapse = () => setIsNavCollapsed(!isNavCollapsed);

  return (
    <>
      <nav className="navbar navbar-expand-lg bg-body-tertiary" style={{ padding: '0' }}>
        <div className="container-fluid " style={{ backgroundColor: '#399a88', padding: '10px' }}>
          <Link className="navbar-brand text-dark fw-bolder" to="/">
            Goods to find
          </Link>
          <button
            className="navbar-toggler text-dark"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02"
            aria-expanded="!isNavCollapsed ? true : false"
            aria-label="Toggle navigation"
            onClick={handleNavCollapse}
          >
            <span className="navbar-toggler-icon "></span>
          </button>
          <div
            className={`${isNavCollapsed && 'collapse'} navbar-collapse`}
            id="navbarTogglerDemo02"
          >
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link className="nav-link text-dark" aria-current="page" to="/">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link text-dark" aria-disabled="true" to="/register">
                  Register
                </Link>
              </li>
              <li className="nav-item ">
                <Link className="nav-link text-dark" aria-disabled="true" to="/login">
                  Login
                </Link>
              </li>
              <li className="nav-item ">
                <Link className="nav-link text-dark" aria-disabled="true" to="/category">
                  Manage Categories
                </Link>
              </li>
              <li className="nav-item ">
                <Link className="nav-link text-dark" aria-disabled="true" to="/adverts">
                  Manage Adverts
                </Link>
              </li>
              <li className="nav-item ">
                <Link className="nav-link text-dark" aria-disabled="true" to="/advertsforusers">
                  Adverts
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </>
  );
}

export default NavBar;