import { useNavigate } from 'react-router-dom';
import './HomePage.css';
import homePageImg from '../../assets/homePageImg.jpg';

function HomePage() {
  const navigate = useNavigate();

  return (
    <>
      <div className="container-fluid text-center mt-5 home-page-style">
        <div className="row justify-content-center row-cols-1 row-cols-lg-4">
          <div className="col mb-5">
            <img
              src={homePageImg}
              alt="book"
              className="img"
            />
          </div>
          <div className="col text-start m-5">
            <h1>Look and see. Maybe there is something you want buy.</h1>
            <p>
              Welcome to our vibrant advertisement page, where you can discover a wide array of goods tailored to meet your needs.
              From the latest tech gadgets and stylish apparel to home essentials and unique handcrafted items,
              our platform connects buyers and sellers in a seamless, user-friendly environment.
              Whether you're looking to buy, sell, or simply browse, you'll find everything you need right here,
              backed by detailed listings and secure transactions. Explore our diverse offerings and find your next great deal today!
            </p>
            <button type="button" className="btn home-button me-2 mt-2" onClick={() => navigate(`/register`)}>Register</button>
            <button type="button" className="btn home-button mt-2" onClick={() => navigate(`/login`)}>Login</button>
          </div>
        </div>
      </div>
    </>
  );
}

export default HomePage;
