const Config = {
  version: 0.1,

  apiDomain: 'http://localhost:8080',

  endpoints: {
    auth: {
      register: '/auth/register',
      login: '/auth/login'
    },
    category: {
      create: '/category',
      update: '/category/{id}',
      getAll: '/category',
      delete: '/category/{id}'
    },
    advert: {
      create: '/advert',
      update: '/advert/{id}',
      getAll: '/advert',
      getOne: '/advert/{id}',
      delete: '/advert/{id}'
    },
    comment: {
      create: '/comment',
      delete: '/comment',
      getAll: '/comment'
    }
  }
};

export default Config;