import "./sidebar.css";

export default function SideBar(){
    return (
        <nav className={"sidebar-container"}>
            <ul className={"sidebar-list"}>
              <li className={"sidebar-list-item"}><a href={"https://en.wikipedia.org/wiki/Korfball"} target={"_blank"}>Korfball Wikipedia</a></li>
              <li className={"sidebar-list-item"}><a href={"https://www.youtube.com/watch?time_continue=151&v=GUtJB5jBOis&embeds_referring_euri=https%3A%2F%2Fwww.bing.com%2F&embeds_referring_origin=https%3A%2F%2Fwww.bing.com&source_ve_path=MjM4NTE"} target={"_blank"}>Korfball Rules Video</a></li>
              <li className={"sidebar-list-item"}><a href={"https://eskvattila.nl/attila-tournament/"} target={"_blank"}>Attila</a></li>
              <li className={"sidebar-list-item"}><a href={"https://www.instagram.com/scotlandkorf/"} target={"_blank"}>SKA Social Media</a></li>
              <li className={"sidebar-list-item"}><a href={"https://englandkorfball.co.uk/korfball/origins-of-korfball/"} target={"_blank"}>Korfball History</a></li>
            </ul>
        </nav>
    )
}