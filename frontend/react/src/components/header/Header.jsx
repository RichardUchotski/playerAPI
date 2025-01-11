import BallPicture from "../../assets/korfball-ball.png";
import {Link} from "react-router-dom";

export default function Header() {
    return (
        <header className={"main-header"}>
            <div className={"main-header__outer-container"}>
                <div className={"main-header__inner-title-container"}>
                    <h1 className={"main-header__h1"}>Scottish Korfball</h1>
                    <img src={BallPicture} alt={"A picture of a korfball"} className={"main-header__ballPic"}/>
                </div>
                <div className={"main-header__inner-menu-container"}>
                    <div className={"main-header__hamburger_menu"}>
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                    <nav className={"main-header__dropdown-nav"}>
                        <ul className={"main_header__dropdown-ul"}>
                            <li className={"main_header__dropdown-lItem"}><Link to={"/"}>Home</Link></li>
                            <li className={"main_header__dropdown-lItem"}><Link to={"/"}>What is Korfball?</Link></li>
                            <li className={"main_header__dropdown-lItem"}><Link to={"/"}>Player Registration</Link></li>
                            <li className={"main_header__dropdown-lItem"}><Link to={"/"}>Scottish Korfball Team</Link></li>
                            <li className={"main_header__dropdown-lItem"}><Link to={"/"}>Clubs</Link></li>
                            <li className={"main_header__dropdown-lItem"}><Link to={"/"}>Tournaments</Link></li>
                        </ul>
                    </nav>
                </div>
            </div>
            <nav className={"main-header__nav"}>
                <ul className={"main_header__ul"}>
                    <li className={"main_header__lItem"}><Link to={"/"}>Home</Link></li>
                    <li className={"main_header__lItem"}><Link to={"/"}>What is Korfball?</Link></li>
                    <li className={"main_header__lItem"}><Link to={"/player-registration"}>Player Registration</Link></li>
                    <li className={"main_header__lItem"}><Link to={"/"}>Scottish Korfball Team</Link></li>
                    <li className={"main_header__lItem"}><Link to={"/"}>Clubs</Link></li>
                    <li className={"main_header__lItem"}><Link to={"/"}>Tournaments</Link></li>
                </ul>
            </nav>
        </header>
    )
}