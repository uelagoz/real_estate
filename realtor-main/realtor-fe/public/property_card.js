// Generic onclick handlers for 'View Details' and 'Save' buttons
const onViewDetailsButtonClicked = (propertyId) => {
    const requestUrl = "http://localhost:3000/property.html?propertyId=" + propertyId;
    window.location.assign(requestUrl);
};

const onSaveButtonClicked = (elem, propertyId) => {
    const requestUrl = SERVER_BASE + "/property/save";
    const requestBody = JSON.stringify({
        propertyId: propertyId
    });
    fetch(requestUrl, {
        method: "PUT",
        body: requestBody,
        credentials: "include"
    }).then(response => {
        if (response.status === 200) {  // YOU SURE?
            // Display success information
            // Change the text as "Unsave" and onclick handler
        }
    }).catch(reason => {
        // Display fail information
    });
};


function createLocationText(prop) {
    const locationText = document.createElement("p");
    locationText.classList += "card-text";
    const locationTag = document.createElement("strong");
    locationTag.innerText = "Location: ";
    locationText.appendChild(locationTag);
    locationText.innerHTML += prop.district + " / " + prop.city;
    return locationText;
}

function createPropertyTitle(prop) {
    const propertyTitle = document.createElement("h5");
    propertyTitle.classList += "card-title";
    propertyTitle.innerText = prop.propertyTitle;
    return propertyTitle;
}

function createViewDetailsButton(prop) {
    const btnViewDetails = document.createElement("a");
    btnViewDetails.href = "#";
    btnViewDetails.classList += "btn btn-primary";
    btnViewDetails.addEventListener("click", () => onViewDetailsButtonClicked(prop.propertyId));
    btnViewDetails.innerText = "View Details";
    return btnViewDetails;
}

function createSaveButton(prop) {
    const btnSave = document.createElement("button");
    btnSave.classList += "btn btn-outline-secondary";
    btnSave.innerText = "Save";
    return btnSave;
}

function createPriceText(prop) {
    const priceText = document.createElement("p");
    const priceTag = document.createElement("strong");
    priceTag.innerText = "Price:"
    priceText.appendChild(priceTag);
    priceText.innerHTML += " " + prop.price + " TL";
    return priceText;
}

function createPropertyImage(prop) {
    const propertyImage = document.createElement("img");
    // propertyImage.src = "https://via.placeholder.com/300x200";
    propertyImage.src = (prop.propertyImage) ? prop.propertyImage : "https://via.placeholder.com/300x200";
    propertyImage.alt = "Property Image";
    return propertyImage;
}

function createButtonGroup(btnViewDetails, btnSave) {
    const btnGroup = document.createElement("div");
    btnGroup.classList += "btn-group";
    btnGroup.appendChild(btnViewDetails);
    btnGroup.appendChild(btnSave);
    return btnGroup;
}

function createPropertyDetailsDiv(propertyTitle, locationText, priceText, btnGroup) {
    const propertyDetailsDiv = document.createElement("div");
    propertyDetailsDiv.classList += "property-details";
    propertyDetailsDiv.appendChild(propertyTitle);
    propertyDetailsDiv.appendChild(locationText);
    propertyDetailsDiv.appendChild(priceText);
    propertyDetailsDiv.appendChild(btnGroup);
    return propertyDetailsDiv;
}

function createPropertyCard(propertyImage, propertyDetailsDiv) {
    const propertyCard = document.createElement("div");
    propertyCard.classList += "card property-card";
    propertyCard.appendChild(propertyImage);
    propertyCard.appendChild(propertyDetailsDiv);
    return propertyCard;
}

function createPropertyElement(prop) {
    const btnViewDetails = createViewDetailsButton(prop);
    const btnSave = createSaveButton(prop);
    const btnGroup = createButtonGroup(btnViewDetails, btnSave);
    const propertyTitle = createPropertyTitle(prop);
    const locationText = createLocationText(prop);
    const priceText = createPriceText(prop);
    const propertyDetailsDiv = createPropertyDetailsDiv(propertyTitle, locationText, priceText, btnGroup);
    const propertyImage = createPropertyImage(prop);
    const propertyCard = createPropertyCard(propertyImage, propertyDetailsDiv);
    const columnDiv = document.createElement("div");
    columnDiv.classList += "col-md-4";
    columnDiv.appendChild(propertyCard);
    propertyContainer.appendChild(columnDiv);
}