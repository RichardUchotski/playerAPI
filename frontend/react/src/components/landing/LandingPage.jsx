import Header from "../header/Header.jsx";
import "../header/header.css";
import LandingMainContent from "./LandingMainContent.jsx";
import "./landing.css";

export default function LandingPage() {
    return <main className={"main-landing"}>
        <LandingMainContent />
    </main>
}