import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { useNavigate } from 'react-router-dom';
import './AdvertCard.css';
import shopBagImg from '../../assets/shopBagImg.jpg';

function AdvertCard({ advert }) {
  const navigate = useNavigate();
  const { price, title, categoryName, id } = advert;

  return (
    <div className="col p-4">
      <Card className="advert-card">
        <div>
          <Card.Img className="image-size" variant="top" src={shopBagImg} />
        </div>
        <Card.Body>
          <Card.Title>{title}</Card.Title>
          <Card.Text>{categoryName}</Card.Text>
          <Card.Text>Eur {price}</Card.Text>
          <Button
            className="read-more-button ms-3"
            variant="primary"
            onClick={() => navigate(`/details/${id}`)}
          >
            Read More
          </Button>
        </Card.Body>
      </Card>
    </div>
  );
}

export default AdvertCard;
