import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import TEAM_DATA from "../data/teamdata.json";
import { updatePlayer} from "../services/cleint.js";
import {errorNotification, successNotification} from "../services/notification.js";

const team_data = TEAM_DATA.sort((a, b) => a.teamName.localeCompare(b.teamName));

const dateEighteenYearsAgo = new Date();
const dateOneHundredYearsAgo = new Date();

dateEighteenYearsAgo.setFullYear(dateEighteenYearsAgo.getFullYear() - 18);
dateOneHundredYearsAgo.setFullYear(dateOneHundredYearsAgo.getFullYear() - 100);

const MyTextInput = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status="error" mt={0} borderRadius={5}>
                    <AlertIcon />
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};
const MySelect = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status="error" borderRadius={5}>
                    <AlertIcon />
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const UpdateForm = ({playerId, fetchPlayers, ...rest}) => {
    return (
        <>
            <Formik
                initialValues={{
                    firstName: rest.firstName,
                    lastName: rest.lastName,
                    age: rest.age,
                    dateOfBirth: rest.dateOfBirth,
                    phoneNumber: rest.phoneNumber,
                    email: rest.email,
                    gender: rest.gender,
                    team: rest.team,
                }}
                validationSchema={Yup.object({
                    firstName: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    lastName: Yup.string()
                        .max(20, 'Must be 20 characters or less')
                        .required('Required'),
                    age: Yup.number()
                        .min(18, "Must be at least 18")
                        .max(100, "You must be 100 years or less")
                        .required('Required'),
                    dateOfBirth: Yup.date()
                        .transform((value, originalValue) => originalValue ? new Date(originalValue) : value)
                        .min(dateOneHundredYearsAgo, 'Oldest you can be is 100')
                        .max(dateEighteenYearsAgo, 'Youngest you can be is 18')
                        .required('Required'),
                    phoneNumber: Yup.string()
                        .matches(/^07\d{9}$|^\+44\d{10}$/, 'Phone number must be 11 digits starting with 07 or start with +44 followed by 10 digits')
                        .required('Phone number is required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    gender: Yup.string()
                        .oneOf(
                            ['female', 'gender_fluid', 'male', 'non_binary', 'prefer_not_to_say'],
                            'Invalid gender'
                        )
                        .required('Required'),
                    team: Yup.string()
                        .oneOf(
                            team_data.map(teamObject => teamObject.teamName),
                            'Need to select a team'
                        )
                        .required('Required'),
                })}

                onSubmit={(values, { setSubmitting }) => {
                    values.age = new Date().getFullYear() - new Date(values.dateOfBirth).getFullYear();
                    setSubmitting(true);
                    alert(values);
                    alert(JSON.stringify(values))
                    updatePlayer(playerId, values).then(res => {
                        console.log(res)
                        successNotification(
                            "Player updated",
                            `${values.firstName} was successfully updated`
                        )
                        fetchPlayers();
                    }).catch((err) => {
                        if (err.response) {
                            console.error("Error Data:", err.response.data);
                            console.error("Status Code:", err.response.status);
                            console.error("Headers:", err.response.headers);
                            errorNotification(err.code,err.response.data.message)
                        } else if (err.request) {
                            console.error("No response received:", err.request);
                        } else {
                            console.error("Axios error:", err.message);
                        }
                    }).finally(() =>{
                        setSubmitting(false);
                    })

                }}
            >
                {({ setFieldValue, isValid, isSubmitting }) => (
                    <Form>
                        <Stack>
                            <MyTextInput
                                label="First Name"
                                name="firstName"
                                type="text"
                                placeholder="Enter a name"
                            />

                            <MyTextInput
                                label="Last Name"
                                name="lastName"
                                type="text"
                                placeholder="Enter a last name"
                            />

                            <MyTextInput
                                label="Date of Birth"
                                name="dateOfBirth"
                                type="date"
                                placeholder="Enter birth date"
                                onChange={(e) => {
                                    setFieldValue("dateOfBirth", e.target.value);
                                    const birthYear = new Date(e.target.value).getFullYear();
                                    if (!isNaN(birthYear)) {
                                        const calculatedAge = new Date().getFullYear() - birthYear;
                                        setFieldValue("age", calculatedAge);
                                    }
                                }}
                            />

                            <MyTextInput
                                label="Age"
                                name="age"
                                type="number"
                                placeholder="Age will be calculated"
                                disabled
                            />

                            <MyTextInput
                                label="Phone Number"
                                name="phoneNumber"
                                type="text"
                                placeholder="Enter your phone number"
                            />

                            <MyTextInput
                                label="Email Address"
                                name="email"
                                type="email"
                                placeholder="jane@formik.com"
                            />

                            <MySelect label="Gender" name="gender">
                                <option value="">Select a gender</option>
                                <option value="female">Female</option>
                                <option value="gender_fluid">Gender Fluid</option>
                                <option value="male">Male</option>
                                <option value="non_binary">Non-Binary</option>
                                <option value="prefer_not_to_say">Prefer not to say</option>
                            </MySelect>

                            <MySelect label="Team" name="team">
                                <option value="">Select a team</option>
                                {team_data.map(teamObject => (
                                    <option key={teamObject.teamName} value={teamObject.teamName}>
                                        {teamObject.teamName}
                                    </option>
                                ))}
                            </MySelect>


                            <Button type="submit" colorScheme="blue"  isDisabled={!isValid || isSubmitting} minW={150} maxW={250} textAlign={"center"}  p={4} mt={4}>
                                Submit
                            </Button>

                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default UpdateForm;
