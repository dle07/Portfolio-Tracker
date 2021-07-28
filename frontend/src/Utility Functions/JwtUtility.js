
import cookie from 'react-cookies';

export const saveJwtToCookie = ( HttpResponse ) =>{
  const hours_in_miliseconds = 1000 * 60 * 60 * 24 *3; // 3 hours
  console.log(HttpResponse);
  const jwt_token = HttpResponse.data.jwtToken;
  const expiration = new Date();
  expiration.setDate(Date.now() + hours_in_miliseconds);

  cookie.save("jwt_token", jwt_token, { 
    path:'/',     //accessible on all paths of the server
    secure: true, // Only accessible through https
    expires: expiration //  How long until the cookie expires
});
}