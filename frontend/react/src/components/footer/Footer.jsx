import {Link} from "react-router-dom";
import "./Footer.css";

export default function Footer() {

    return (
        <footer className={"footer"}>
            <ul className={"footer__list"}>
                <li className={"footer_lItem"}><Link to={"/"} className={"link"}>Legal</Link></li>
                <li className={"footer__lItem"}><Link to={"/"} className={"link"}>Contact Us</Link></li>
            </ul>
        </footer>
    )
}