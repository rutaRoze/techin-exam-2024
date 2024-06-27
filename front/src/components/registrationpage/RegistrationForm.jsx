import { useEffect, useState } from 'react';
import { Button, Card, Col, Container, Form, Row } from 'react-bootstrap';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { ErrorMessage } from '@hookform/error-message';
import { useNavigate } from 'react-router-dom';
import Config from '../config/Config';
import ModalSuccess from '../modal/ModalSuccess.jsx';

const RegistrationForm = () => {
  const [showModal, setShowModal] = useState(false);
  const [emailError, setEmailError] = useState('');
  const [dataSaveError, setDataSaveError] = useState('');
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    watch,
    control,
    formState: { errors },
    clearErrors
  } = useForm({
    reValidateMode: 'onChange',
    defaultValues: {
      name: '',
      surname: '',
      email: '',
      password: ''
    },
    criteriaMode: 'all'
  });

  const password = watch('password');

  const handleFormSubmit = async (formData) => {
    setEmailError('');
    clearErrors();

    const url = Config.apiDomain + Config.endpoints.auth.register;

    axios
      .post(url, formData)
      .then((response) => {
        setShowModal(true);
      })
      .catch((error) => {
        if (error.response && error.response.data) {
          const statusCode = error.response.status;
          if (statusCode === 409) {
            setEmailError('Email already exists');
          }
        } else {
          setDataSaveError('Problem while saving data. Please try again');
        }
      });
  };

  const closeModal = () => {
    setShowModal(false);
    navigate('/login');
  };

  useEffect(() => {
    clearErrors();
    setEmailError('');
  }, [clearErrors]);

  return (
    <>
      <Container className="registration-form-container mb-5">
        <Row className="justify-content-md-center">
          <Col xs="12" sm="10" md="8" lg="6">
            <Card className="my-5 border-0">
              <h2 style={{ textAlign: 'center', fontWeight: "bold"}}>
                User Registration
              </h2>
            </Card>
            <Form noValidate onSubmit={handleSubmit(handleFormSubmit)}>
              {(emailError || dataSaveError) && (
                <p style={{ color: 'red' }}>
                  {emailError || dataSaveError}
                </p>
              )}
              <Row>
                <Col xs="12" sm="6">
                  <Form.Group className="mb-3">
                    <Form.Label htmlFor="name" className="d-flex justify-content-start">Name</Form.Label>
                    <Form.Control
                      type="text"
                      name="name"
                      id="name"
                      autoComplete="name"
                      placeholder="Enter your name"
                      {...register('name', {
                        required: 'This field is required',
                        minLength: {
                          value: 2,
                          message: 'Name must contain at least 2 characters'
                        },
                        maxLength: {
                          value: 30,
                          message: 'Name must contain less than 30 characters'
                        },
                        pattern: {
                          value: /^[a-zA-ZąčęėįšųūžĄČĘĖĮŠŲŪŽ]+$/,
                          message: 'Name must contain only letters'
                        }
                      })}
                    />
                    <ErrorMessage
                      errors={errors}
                      name="name"
                      render={({ messages }) =>
                        messages &&
                        Object.entries(messages).map(([type, message]) => (
                          <p className="text-danger mb-1 " style={{ fontSize: '14px' }}
                             key={type}>
                            {message}
                          </p>
                        ))
                      }
                    />
                  </Form.Group>
                </Col>
                <Col xs="12" sm="6">
                  <Form.Group className="mb-3">
                    <Form.Label htmlFor="surname" className="d-flex justify-content-start">Surname</Form.Label>

                    <Form.Control
                      type="text"
                      name="surname"
                      id="surname"
                      placeholder="Enter your surname"
                      {...register('surname', {
                        required: 'This field is required',
                        minLength: {
                          value: 2,
                          message: 'Surname must contain at least 2 characters'
                        },
                        maxLength: {
                          value: 30,
                          message: 'Surname must contain less than 30 characters'
                        },
                        pattern: {
                          value: /^[a-zA-ZąčęėįšųūžĄČĘĖĮŠŲŪŽ]+$/,
                          message: 'Surname must contain only letters'
                        }
                      })}
                    />
                    <ErrorMessage
                      errors={errors}
                      name="surname"
                      render={({ messages }) =>
                        messages &&
                        Object.entries(messages).map(([type, message]) => (
                          <p className="text-danger mb-1" style={{ fontSize: '14px' }}
                             key={type}>
                            {message}
                          </p>
                        ))
                      }
                    />
                  </Form.Group>
                </Col>
              </Row>
              <Form.Group className="mb-3">
                <Form.Label htmlFor="email" className="d-flex justify-content-start">Email</Form.Label>
                <Form.Control
                  name="email"
                  id="email"
                  placeholder="example@example.com"
                  autoComplete="email"
                  {...register('email', {
                    required: 'This field is required',
                    pattern: {
                      value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                      message: 'Email address does not mach the pattern'
                    }
                  })}
                />
                <ErrorMessage
                  errors={errors}
                  name="email"
                  render={({ messages }) =>
                    messages &&
                    Object.entries(messages).map(([type, message]) => (
                      <p className="text-danger mb-1" style={{ fontSize: '14px' }} key={type}>
                        {message}
                      </p>
                    ))
                  }
                />
              </Form.Group>
              <Row>
                <Col xs="12" sm="6">
                  <Form.Group className="mb-3">
                    <Form.Label htmlFor="password" className="d-flex justify-content-start">Password</Form.Label>

                    <Form.Control
                      type="password"
                      name="password"
                      id="password"
                      placeholder="Enter password"
                      autoComplete="new-password"
                      {...register('password', {
                        required: 'This field is required',
                        minLength: {
                          value: 8,
                          message: 'Password must be at least 8 characters long'
                        },
                        maxLength: {
                          value: 30,
                          message: 'Password must not exceed 30 characters.'
                        },
                        pattern: {
                          value: /^(?!.*\s)(?=.*[A-Z])(?=.*\d)(?=.*[a-z])(?=.*[!@#$%^&*()]).+$/,
                          message: 'Password must contain only lowercase, uppercase latin letters, numbers and special symbols !@#$%^&*()'
                        }
                      })}
                    />
                    <ErrorMessage
                      errors={errors}
                      name="password"
                      render={({ messages }) =>
                        messages &&
                        Object.entries(messages).map(([type, message]) => (
                          <p className="text-danger mb-1" style={{ fontSize: '14px' }}
                             key={type}>
                            {message}
                          </p>
                        ))
                      }
                    />
                  </Form.Group>
                </Col>
                <Col xs="12" sm="6">
                  <Form.Group className="mb-3">
                    <Form.Label htmlFor="confirm_password" className="d-flex justify-content-start">
                      Confirm Password
                    </Form.Label>
                    <Form.Control
                      type="password"
                      name="confirm_password"
                      id="confirm_password"
                      placeholder="Confirm password"
                      autoComplete="new-password"
                      {...register('confirm_password', {
                        required: 'This field is required',
                        validate: (value) =>
                          value === password || 'Passwords do not match!'
                      })}
                    />
                    <ErrorMessage
                      errors={errors}
                      name="confirm_password"
                      render={({ messages }) =>
                        messages &&
                        Object.entries(messages).map(([type, message]) => (
                          <p className="text-danger mb-1" style={{ fontSize: '14px' }}
                             key={type}>
                            {message}
                          </p>
                        ))
                      }
                    />
                  </Form.Group>
                </Col>
              </Row>

              <Button type="submit" className="mt-3" style={{ backgroundColor: '#399a88', color: '#000000', border: 'none' }}>
                Register
              </Button>
            </Form>
          </Col>
        </Row>
      </Container>
      <ModalSuccess show={showModal} handleClose={closeModal} customMessage={"Registration successful"} />
    </>
  );
};

export default RegistrationForm;
