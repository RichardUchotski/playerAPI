import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogContent,
    AlertDialogOverlay,
    Button, useDisclosure,
} from '@chakra-ui/react'

import {deletePlayerById} from "../services/cleint.js";
import {useRef} from 'react';
import {errorNotification, successNotification} from "../services/notification.js";

export default function DeleteAlertDialog({ id, fetchPlayers, firstName}) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()

    const deleteText = `\u{1F480} Delete Player \u{1F480}`

    return (
        <>
            <Button colorScheme='red' onClick={onOpen}>
                {deleteText}
            </Button>

            <AlertDialog
                isOpen={isOpen}
                leastDestructiveRef={cancelRef}
                onClose={onClose}
            >
                <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                            Delete Player
                        </AlertDialogHeader>

                        <AlertDialogBody>
                            Are you sure? You can't undo this action afterwards.
                        </AlertDialogBody>

                        <AlertDialogFooter>
                            <Button ref={cancelRef} onClick={onClose}>
                                Cancel
                            </Button>
                            <Button colorScheme='red' onClick={ () => {
                                deletePlayerById(id)
                                    .then(()=>{
                                        fetchPlayers();
                                        successNotification('Player deleted', `${firstName} was successfully deleted.`)
                                    })
                                    .catch(err =>{
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
                                    }

                                )
                                onClose();
                            }} ml={3}>
                                Delete
                            </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                </AlertDialogOverlay>
            </AlertDialog>
        </>
    )
}

