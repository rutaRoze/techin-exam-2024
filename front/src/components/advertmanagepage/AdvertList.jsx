import axios from 'axios';
import { useEffect, useState } from 'react';
import Config from '../config/Config.js';
import { useNavigate } from 'react-router-dom';

function AdvertList() {
  const [advertList, setAdvertList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [search, setSearch] = useState('');
  const navigate = useNavigate();

  const getAdvertList = () => {
    const url = Config.apiDomain + Config.endpoints.advert.getAll;

    axios
      .get(url)
      .then((response) => {
        setAdvertList(response.data);
        console.log([advertList])
        setIsLoading(false);
      })
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    getAdvertList();
  }, []);

  const deleteAdvert = (id) => {
    const url = `${Config.apiDomain}${Config.endpoints.advert.delete.replace('{id}', id)}`;

    axios
      .delete(url)
      .then(() => {
        getAdvertList();
      })
      .catch((error) => console.error(error));
  };

  return (
    <>
      {isLoading && "Data is loading..."}

      <h3 className="mt-5 fw-bolder">Book List</h3>
      <div className="container">
        <form>
          <div className="my-5">
            <input
              type="text"
              onChange={(event) => setSearch(event.target.value)}
              className="form-control"
              placeholder="Search for book by title or category"
            />
          </div>
        </form>

        <table className="table table-striped table-hover mt-5 mb-5">
          <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Title</th>
            <th scope="col">Category</th>
            <th scope="col">Mange advert</th>
          </tr>
          </thead>
          <tbody>
          {advertList
            .filter((advert) =>
                search.trim() === '' ||
                advert.title.toLowerCase().includes(search.toLowerCase()) ||
                advert.categoryName.toLowerCase().includes(search.toLowerCase())
              )
            .map((advert) => (
              <tr key={advert.id}>
                <th scope="row">{advert.id}</th>
                <td>{advert.title}</td>
                <td>{advert.categoryName}</td>
                <td>
                  <button
                    type="button"
                    onClick={() => navigate(`/details/${advert.id}`)}
                    className="btn btn-success me-2"
                  >
                    See Details
                  </button>
                  <button
                    type="button"
                      onClick={() => navigate(`/update/${advert.id}`)}
                    className="btn btn-warning me-2"
                  >
                    Update
                  </button>
                  <button
                    type="button"
                    // disabled={category.isDeleting}
                    onClick={() => deleteAdvert(advert.id)}
                    className="btn btn-danger"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default AdvertList;
