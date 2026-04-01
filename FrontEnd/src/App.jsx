
import {
    Admin,
    Create, EditGuesser,
    ListGuesser,
    ReferenceManyField,
    Resource,
    Show, SimpleForm,
    SimpleShowLayout,
    DataTable, ArrayField, SingleFieldList, ChipField,
    TextField,
    TextInput,
    useDelete, useRecordContext,
    List, NumberInput, Edit, ReferenceField, SelectArrayInput, ArrayInput, SimpleFormIterator, Button, FormDataConsumer,
    ReferenceArrayInput, required
} from "react-admin";
import simpleRestPrvider from "ra-data-simple-rest";
import { fetchUtils} from 'react-admin';
import authProvider from "./authProvider";
import {meta} from "eslint-plugin-react-hooks";

const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({ Accept: 'application/json' }); //creating a header to get data in json
    }
    const { token } = JSON.parse(localStorage.getItem('auth'));
    //token in {} would get the token from auth
    //getting the token from auth by getting it from local storage and turning it back to js obj
    options.headers.set('Authorization', `Bearer ${token}`);
    // this adds the header Authorization: Bearer which needed for each access
    return fetchUtils.fetchJson(url, options);
    // here where it hit the api, fetchUtils will convert to json and return the data

}

const StudentsShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField label="ID" source="id"/>
            <TextField label="Student Name" source="name"/>
            <TextField label="Student Age" source="age"/>
            <DataTable.Col source="assignedCourses">
                <ArrayField source="assignedCourses">
                    <SingleFieldList>
                        <ReferenceField source="id"
                                        reference="Courses"
                                        link="show">
                        <ChipField source="Name" />
                        </ReferenceField>
                    </SingleFieldList>
                </ArrayField>
            </DataTable.Col>

        </SimpleShowLayout>

    </Show>
)

const StudentValidation = (inputs) => {
    const errors = {};
    if (!inputs.name) {
        errors.name = 'Name is requires';
    }
    if (!inputs.age){
        errors.age = 'ra.validation.required';
    } else if (inputs.age < 18) {
        errors.age = 'Age must be 18 or older';
    }
    return errors;
}


const createStudent = () => (
    <Create>
        <SimpleForm validate={StudentValidation}>
            <TextInput label="Student Name" source="name" validate={required()}/>
            <TextInput lable="Student Age" source="age" validate={required()}/>
        </SimpleForm>
    </Create>
);

const DeleteStudent = () => {
    const record = useRecordContext();
    const [deleteStudent, {isPending, error}] = useDelete();
    const handelClick = () => {
        deleteStudent(
            'students',
            {id: record.id, previousData: record}
        );
    }
    if (error) {return <p>ERROR</p>;}
    return <button disabled={isPending} onClick={handelClick}>Delete</button>
};


const StudentList = () => (
    <List>
        <DataTable>
            <DataTable.Col source="id" />
            <DataTable.Col source="name" />
            <DataTable.NumberCol source="age" />
            <DataTable.Col source="assignedCourses">
                <ArrayField source="assignedCourses">
                    <SingleFieldList>
                        <ReferenceField source="id"
                                        reference="Courses"
                                        link="show">
                            <ChipField source="Name" />
                        </ReferenceField>
                    </SingleFieldList>
                </ArrayField>
            </DataTable.Col>
        </DataTable>
    </List>
);

const transformStudent = (data) => {
    return {
        name: data.name, // baisc mapping
        age: data.age,

        //saving the object id to be sent to the server
        //... convert set into an array so server accepts it
        courseIds: data.assignedCourses ? [...new Set(data.assignedCourses.map(item =>
            typeof item === 'object' ? item.id : item //type checking
        ))] : [] //ternary operator at base if there are no courses, then return empty array
    };
};
export const StudentEdit = () => (
    <Edit transform={transformStudent}>
        <SimpleForm>
            <TextInput source="name" />
            <NumberInput source="age" />
            <ReferenceArrayInput source="assignedCourses" reference="Courses">
                <SelectArrayInput optionText="Name" />
            </ReferenceArrayInput>
        </SimpleForm>
    </Edit>
);

const CoursesShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField label="ID" source="id"/>
            <TextField label="Course Name" source="Name"/>
            <TextField label="Course Level" source="Level"/>
        </SimpleShowLayout>
    </Show>
)
const CoursesDelete = () => {
    const record = useRecordContext();
    const [deleteCourse, {isPending, error}] = useDelete();
    const handelClick = () => {
        deleteCourse(
            'Courses',
            {id: record.id, previousData: record}
        );
    }
    if (error) {return <p>ERROR</p>;}
    return <button disabled={isPending} onClick={handelClick}>Delete</button>
}
const dataProvider = simpleRestPrvider('http://localhost:8081', httpClient);


const App = () => (
        <Admin dataProvider={dataProvider}
               authProvider={authProvider}>
            <Resource name="students" list={StudentList} show={StudentsShow} create={createStudent}
                      edit={StudentEdit} delete={DeleteStudent} />
            <Resource name="Courses" list={ListGuesser} show={CoursesShow} delete={CoursesDelete}/>
    </Admin>
);
export default App


