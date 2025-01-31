import {
    Button,
    Drawer, DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Flex, Stack, useDisclosure
} from "@chakra-ui/react";
import SignupForm from "./CreateCustomerForm.jsx";
import {deleteAllCustomers} from "../services/cleint.js";


const AddIcon = () => "+";
const CloseIcon = () => "X";
const SkullIcon = () => '\u2620'

const DrawerForm = ({isApp, fetchPlayers, isOnCard}) => {

    const { isOpen, onOpen, onClose } = useDisclosure();

    return  (
        <>
            <Flex gap={4} justify={"space-between"}>
                <Button
                    maxW={200}
                    leftIcon={<AddIcon/>}
                    colorScheme={"green"}
                    onClick={onOpen}
                >
                    Register a Player
                </Button>
                {!isApp &&  <Button
                    maxW={200}
                    leftIcon={<SkullIcon/>}
                    colorScheme={"red"}
                    onClick={deleteAllCustomers}
                >
                    Delete All Players
                </Button> }

            </Flex>

            <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Register for Korfball</DrawerHeader>

                    <DrawerBody>
                        <SignupForm fetchPlayers={fetchPlayers} />
                    </DrawerBody>

                    <DrawerFooter>
                        <Button
                            leftIcon={<CloseIcon/>}
                            colorScheme={"red"}
                            onClick={onClose}
                        >
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    )
}

export default DrawerForm;

