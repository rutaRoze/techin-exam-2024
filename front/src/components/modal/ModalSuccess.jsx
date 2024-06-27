import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

const ModalSentRequestSuccess = ({show, handleClose, customMessage}) => {

  return (
    <>
      <Modal show={show} onHide={handleClose} data-testid="modal-success">
        <Modal.Header>
          <Modal.Title>{customMessage}</Modal.Title>
        </Modal.Header>
        <Modal.Footer>
          <Button style={{ backgroundColor: '#399a88', color: '#000000', border: 'none' }} onClick={handleClose}>
            Continue
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default ModalSentRequestSuccess;