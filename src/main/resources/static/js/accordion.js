const accordions = document.getElementsByClassName("accordion");

for (const accordion of accordions) {
    accordion.addEventListener("click", event => {
        accordion.classList.toggle("accordion_hide");
    });
}
