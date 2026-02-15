
import './App.css'
import {Admin, ListGuesser, Resource} from "react-admin";
import simpleRestPrvider from "ra-data-simple-rest";
import { fetchUtils} from 'react-admin';

import authProvider from "./authProvider";

const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({ Accept: 'application/json' });
    }
    const { token } = JSON.parse(localStorage.getItem('auth'));
    options.headers.set('Authorization', `Bearer ${token}`);
    return fetchUtils.fetchJson(url, options);

};


const dataProvider = simpleRestPrvider('http://localhost:8081', httpClient);


const App = () => (
        <Admin dataProvider={dataProvider}
               authProvider={authProvider}>
            <Resource name="students" list={ListGuesser} />
            <Resource name="Courses" list={ListGuesser} />
            <Resource name="Staff" list={ListGuesser}/>
    </Admin>
);
export default App


