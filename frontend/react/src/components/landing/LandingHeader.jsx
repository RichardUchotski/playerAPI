import LandingNavBar from "./LandingNavBar";
import BallPicture from "../../assets/korfball-ball.png";

export default function LandingHeader() {
    return (
        <>
            <header id="landing-header-container">
                <div className="landing-item"></div>
                <h1 className="landing-item">Korfball Players</h1>
                <img className="landing-item" id="ball-pic" src={BallPicture} alt="logo" width="2.5%" />
            </header>
        </>
    )
}