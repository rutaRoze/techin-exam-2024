import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { Button, Card, Col, Container, Form, Row } from 'react-bootstrap';
import { useAuth } from '../authentication/AuthContext';
import { useForm } from 'react-hook-form';

const LoginForm = () => {
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { login } = useAuth();

  const {
    register,
    handleSubmit,
    formState: { errors },
    clearErrors
  } = useForm({
    reValidateMode: 'onChange',
    defaultValues: {
      email: '',
      password: ''
    },
    criteriaMode: 'firstError'
  });

  const handleLoginSubmit = async (loginData) => {
    const { email, password } = loginData;

    if (!email || !password) {
      setError('User not found');
      return;
    }

    login(email, password, {
      then: () => {
        navigate('/');
      },
      catch: (error) => {
        if (error.response && error.response.status === 401) {
          setError('Invalid email or password');
        } else {
          setError('Login failed');
        }
      }
    });
  };

  useEffect(() => {
    clearErrors();
    setError('');
  }, [clearErrors]);

  return (
    <>
      <Container className="form-container justify-content-md-center">
        <Row className="justify-content-md-center">
          <Col xs="12" sm="8" md="6" lg="4">
            <Card className="my-5 border-0">
              <h2 style={{ textAlign: 'center', fontWeight: "bold" }}>Log in to your account</h2>
            </Card>
            <Form noValidate onSubmit={handleSubmit(handleLoginSubmit)}>
              {error && <p style={{ color: 'red' }}>{error}</p>}
              <Form.Group className="mb-3" controlId="formGroupEmail">
                <Form.Label className="d-flex justify-content-start">Email</Form.Label>
                <Form.Control
                  type="email"
                  autoComplete="email"
                  placeholder="example@example.com"
                  {...register('email', { required: 'This field is required' })}
                />
                {errors.email && (
                  <Form.Text className="text-danger"
                  >{errors.email.message}</Form.Text>
                )}
              </Form.Group>

              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label className="d-flex justify-content-start">Password</Form.Label>
                <Form.Control
                  type="password"
                  autoComplete="new-password"
                  placeholder="enter password"
                  {...register('password', { required: 'This field is required' })}
                />
                {errors.password && (
                  <Form.Text className="text-danger"
                  >{errors.password.message}</Form.Text>
                )}
              </Form.Group>

              <Form.Group className="mb-3" controlId="formGroupButton">
                <Row className="justify-content-center">
                  <Col className="mt-3" xs={12} md={6}>
                    <Button type="submit" style={{ backgroundColor: '#399a88', color: '#000000', border: 'none' }}>
                      Login
                    </Button>
                  </Col>
                </Row>
              </Form.Group>
            </Form>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default LoginForm;
