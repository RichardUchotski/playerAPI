'use client'

import {
    Heading,
    Avatar,
    Box,
    Center,
    Image,
    Flex,
    Text,
    Stack,
    Button,
    useColorModeValue,
    Tag,
} from '@chakra-ui/react'

import DeleteAlertDialog from "./AlertDialog.jsx";

export default function CardWithImage({fetchPlayers, id, firstName,lastName,email, age, phoneNumber, dateOfBirth, gender, team}) {

    let genderForAPICall;

    if(gender === "FEMALE"){
        genderForAPICall = "women";
    }else if(gender === "MALE"){
        genderForAPICall="men";
    }else{
        if(Math.random() > 0.5){
            genderForAPICall="women"
        }else{
            genderForAPICall="men";
        }
    }

    let genderStrings = gender.split("_")

    if(gender !== "PREFER_NOT_TO_SAY"){
        for(let i=0; i<genderStrings.length; i++){
            genderStrings[i] = genderStrings[i].charAt(0) + genderStrings[i].substring(1, genderStrings[i].length).toLowerCase();
        }

        genderStrings = genderStrings.join(" ");
    } else {
        genderStrings = "";
    }

    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                minW={'300px'}
                minH={"550px"}
                w={'full'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit="cover"
                    alt="#"
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://randomuser.me/api/portraits/${genderForAPICall}/${id}.jpg`
                        }
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={ 1} align={'center'} mb={5}>
                        <Tag borderRadius={"full"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'} textAlign={"center"}>
                            {firstName + " " + lastName}
                        </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}>{age}</Text>
                        <Text color={'gray.500'}>{phoneNumber}</Text>
                        <Text color={'gray.500'}>{genderStrings}</Text>
                        <Text color={'gray.500'}>{dateOfBirth}</Text>
                        <Text color={'gray.500'}>{team}</Text>
                    </Stack>

                    <center>
                        <Button onClick={""} m={4}>Update Player Information</Button>
                        <DeleteAlertDialog id={id} fetchPlayers={fetchPlayers} firstName={firstName} />
                    </center>
                </Box>
            </Box>
        </Center>
    )
}