import "./landing.css";

export default function LandingArticle({heading, text, imgSrc}){
    return (
        <article className={"main-landing__article"}>
        <h2 className={"main-landing__article_subheading"}>
            {heading}
        </h2>
        <p className={"main-landing__article_paragraph"}>
            {text}
        </p>
        <img className={"main-landing__article_image"} src={imgSrc} />
    </article>
    )
}