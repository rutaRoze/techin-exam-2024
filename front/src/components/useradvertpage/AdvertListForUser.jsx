import axios from 'axios';
import { useEffect, useState } from 'react';
import './AdvertListForUser.css';
import Config from '../config/Config.js';
import AdvertCard from './AdvertCard.jsx';

const AdvertList = () => {
  const [advertList, setAdvertList] = useState();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const url = Config.apiDomain + Config.endpoints.advert.getAll;
    axios
      .get(url)
      .then((response) => {
        setAdvertList(response.data);
        setIsLoading(false);
      })
      .catch((error) => console.log(error));
  }, []);

  if (isLoading) {
    return <div>Data is loading...</div>;
  }

  return (
    <>
      <div className="background-color-book-list">
        <div
          className="container-fluid text-center align-content-center position-relative background-color-advert-list px-5">
          <div className="row row-cols-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-4 ">
            {advertList.map((advert) => (
              <AdvertCard key={advert.id} advert={advert} />
            ))}
          </div>
        </div>
      </div>
    </>
  )
    ;
};

export default AdvertList;
