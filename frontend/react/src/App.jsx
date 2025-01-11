
import LandingPage from "./components/landing/LandingPage.jsx";
import {BrowserRouter as Router, Routes, Route, useLocation} from "react-router-dom";
import PlayerRegistrationPage from "./components/registration/PlayerRegistrationPage.jsx";
import Header from "./components/header/Header.jsx";
import Footer from "./components/footer/Footer.jsx";
import SideBar from "./components/sidebar/SideBar.jsx";
import "./App.css";
import {useEffect, useState} from "react";
import Dots from "./assets/dots.svg";

function App() {

    const [showSideBar, setShowSideBar] = useState(false);


    const location = useLocation(); // Detects the current route

    // Effect to reset dropdown when the route changes
    useEffect(() => {
        setShowSideBar(false); // Close the dropdown
    }, [location]);

    return (
        <>
                <Header />
                <div className={"main-sidebar-wrapper"}>
                    <img className={"app-dots-image"}
                         src={Dots}
                         onClick={() => setShowSideBar( (prevState) =>!prevState) }  alt={"Three vertical dots to indicate a drop down menu"}/>
                    { showSideBar && <SideBar />}
                </div>
                    <Routes>
                        <Route path={"/"} element={  <LandingPage />} />
                        <Route path={"/player-registration"} element={ <PlayerRegistrationPage />} />
                        <Route path={"/player-registration"} element={ <PlayerRegistrationPage />} />
                        <Route path={"/player-registration"} element={ <PlayerRegistrationPage />} />
                        <Route path={"/player-registration"} element={ <PlayerRegistrationPage />} />
                        <Route path={"/player-registration"} element={ <PlayerRegistrationPage />} />
                        <Route path={"/player-registration"} element={ <PlayerRegistrationPage />} />
                        <Route path={"/player-registration"} element={ <PlayerRegistrationPage />} />
                    </Routes>
                <Footer />
        </>
    )
}
export default App
