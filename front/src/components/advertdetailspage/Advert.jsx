import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Config from '../config/Config.js';
import shopBagImg from '../../assets/shopBagImg.jpg';

function Advert() {
  const [advert, setAdvert] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');
  const { id } = useParams();

  useEffect(() => {
    const url = `${Config.apiDomain}${Config.endpoints.advert.getOne.replace('{id}', id)}`;

    axios
      .get(url)
      .then((response) => {
        setAdvert(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        setErrorMessage(error.message);
        setIsLoading(false);
      });
  }, [id]);

  if (isLoading) {
    return <>Data is loading...</>;
  }

  if (errorMessage) {
    return <>Something is wrong</>;
  }

  const { title, description, price, city, categoryName } = advert;

  return (
    <>
      <div className="container-fluid m-5 book-details-page-style">
        <div className="row">
          <h4 className="card-title fw-bolder d-flex justify-content-start mb-2">Book information</h4>
          <div className="card mb-3" style={{ maxWidth: '540px' }}>
            <div className="row g-0">
              <div className="col-md-4 d-flex justify-content-center align-items-center mt-2 mb-2">
                  <img src={shopBagImg} className="img-fluid rounded-start" alt="default picture" />
              </div>
              <div className="col-md-8">
                <div className="card-body">

                  <p className="card-text fw-bolder fs-5">{title}</p>
                  <p className="card-text">{description}</p>
                  <p className="card-text">Eur: {price}</p>
                  <p className="card-text">{city} pages</p>
                  <p className="card-text">{categoryName}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Advert;
