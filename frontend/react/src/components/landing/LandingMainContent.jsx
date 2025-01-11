import LandingArticle from "./LandingArticle.jsx";
import ScotLogo from "../../assets/scotland_korfball_logo.jpg";
import TeamCheering from "../../assets/teamCheering.jpg";
import HighFive from "../../assets/join-nav.jpg";

export default function LandingMainContent(){
    return (
            <section className={"main-section"}>
                <LandingArticle
                    heading={"Welcome to Scottish Korfball"}
                    text={`Discover the excitement of korfball, Scotland’s most inclusive and dynamic sport. Whether you're new to the game or a seasoned player, our site is your one-stop destination for everything korfball. Learn about the rules of the game, register to play, or explore local clubs and tournaments happening across the country. Scottish korfball is more than a sport—it's a community built on teamwork, inclusivity, and passion for the game.`}
                    imgSrc={ScotLogo}
                />
                <LandingArticle
                    heading={"Explore and Get Involved"}
                    text={`Not sure what korfball is? Start by visiting our "What is Korfball?" page, where you’ll learn the basics of this unique mixed-gender sport. If you’re ready to dive in, check out our Player Registration page to join a team near you. Curious about the national scene? Head over to the Scottish Korfball Team page to find out how you can support or even represent Scotland in competitions.`}
                    imgSrc={TeamCheering}
                />
                <LandingArticle
                    heading={"Be Part of the Action"}
                    text={`From local club matches to national tournaments, there’s always something happening in the world of Scottish korfball. Visit the Tournaments page to stay updated on upcoming events, or explore our Clubs section to find a korfball community in your area. Whether you’re here to play, watch, or cheer, Scottish korfball has a place for everyone. Join us today and be part of the action!`}
                    imgSrc={HighFive}
                />
            </section>
    )
}