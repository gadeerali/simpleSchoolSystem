
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
    List, NumberInput, Edit, ReferenceField, SelectArrayInput, ArrayInput, SimpleFormIterator, Button, FormDataConsumer
} from "react-admin";
import simpleRestPrvider from "ra-data-simple-rest";
import { fetchUtils} from 'react-admin';
import authProvider from "./authProvider";
import {meta} from "eslint-plugin-react-hooks";

const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({ Accept: 'application/json' });
    }
    const { token } = JSON.parse(localStorage.getItem('auth'));
    options.headers.set('Authorization', `Bearer ${token}`);
    return fetchUtils.fetchJson(url, options);

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

const createStudent = () => (
    <Create>
        <SimpleForm>
            <TextInput source="name"/>
            <TextInput source="age"/>
        </SimpleForm>
    </Create>
);

const DeleteStudent = () => {
    const record = useRecordContext();
    const [deleteStudetnt, {isPending, error}] = useDelete();
    const handelClick = () => {
        deleteStudetnt(
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
const StudentEdit = () => (
    <Edit>
        <SimpleForm>
          <TextInput source="id" />
          <TextInput source="name" />
          <NumberInput source="age" />
            <NumberInput label="Course ID" source="courseId" />
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

const dataProvider = simpleRestPrvider('http://localhost:8081', httpClient);


const App = () => (
        <Admin dataProvider={dataProvider}
               authProvider={authProvider}>
            <Resource name="students" list={StudentList} show={StudentsShow} create={createStudent}
                      edit={StudentEdit} delete={DeleteStudent} />
            <Resource name="Courses" list={ListGuesser} show={CoursesShow}/>
            <Resource name="Staff" list={ListGuesser}/>
    </Admin>
);
export default App


