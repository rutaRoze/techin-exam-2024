import axios from 'axios';
import { useEffect, useState } from 'react';
import Config from '../config/Config.js';
import ModalSuccess from '../modal/ModalSuccess.jsx';
import { useNavigate } from 'react-router-dom';

function AdvertRegistration() {
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    price: '',
    city: '',
    categoryId: ''
  });
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const url = Config.apiDomain + Config.endpoints.category.getAll;
    axios.get(url)
      .then(response => {
        setCategories(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the categories!', error);
      });
  }, []);

  const handleForm = (event) => {
    setFormData((previousFormData) => ({
      ...previousFormData,
      [event.target.name]: event.target.value
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    sendDataToBackend();
  };

  const closeModal = () => {
    setShowModal(false);
    navigate("/advert")
  };

  const sendDataToBackend = () => {
    const url = Config.apiDomain + Config.endpoints.advert.create;
    axios
      .post(url, {
        title: formData.title,
        description: formData.description,
        price: formData.price,
        city: formData.city,
        categoryId: formData.categoryId
      })
      .then(() => {
        setShowModal(true);
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <div className="container-fluid background-form ">
        <div className="row justify-content-center">
          <form
            onSubmit={handleSubmit}
            className=" col-8 col-sm-8 col-md-6 col-xl-4 mt-3"
          >
            <fieldset>
              <legend className="mt-5 my-2 fw-normal" style={{ color: 'black', fontWeight: '200' }}>
                Register New Advertisement
              </legend>
              <div className="mb-3">
                <input
                  type="text"
                  required={true}
                  className="form-control"
                  placeholder="Enter advert title"
                  name="title"
                  value={formData.title}
                  onChange={handleForm}
                />
              </div>
              <div className="mb-3">
                <textarea
                  required={true}
                  cols={30}
                  rows={5}
                  className="form-control"
                  placeholder="Enter advert description"
                  name="description"
                  value={formData.description}
                  onChange={handleForm}
                />
              </div>
              <div className="mb-3">
                <input
                  required={true}
                  type="number"
                  className="form-control"
                  placeholder="Enter price"
                  name="price"
                  value={formData.price}
                  onChange={handleForm}
                />
              </div>
              <div className="mb-3">
                <input
                  required={true}
                  type="text"
                  className="form-control"
                  placeholder="Enter city"
                  name="city"
                  value={formData.city}
                  onChange={handleForm}
                />
              </div>
              <div className="mb-3">
                <select
                  required={true}
                  className="form-select"
                  name="categoryId"
                  value={formData.categoryId}
                  onChange={handleForm}
                >
                  <option value="">Select category from list</option>
                  {categories.map(category => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </div>
              <button type="submit" className="create-category-button">
                Add
              </button>
            </fieldset>
          </form>
        </div>
      </div>
      <ModalSuccess show={showModal} handleClose={closeModal} customMessage={'Advertisement added successfully'} />
    </>
  );
}

export default AdvertRegistration;
