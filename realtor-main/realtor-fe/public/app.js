const CLIENT_BASE = "http://localhost:3000";
const SERVER_BASE = "http://localhost:8080/api";

// Session Buttons
const loginBtn = null;
const logoutBtn = null;
const registerBtn = null;

// Filters
const filters = [
    { selector: "city",               default: ""    },
    { selector: "district",           default: ""    },
    { selector: "minPrice",           default: ""    },
    { selector: "maxPrice",           default: ""    },
    { selector: "minMetersPerSquare", default: ""    },
    { selector: "maxMetersPerSquare", default: ""    },
    { selector: "propertyType",       default: "ANY" },
    // { selector: "numberRooms",        default: "ANY" },
    { selector: "minNumberRooms",     default: ""    },
    { selector: "maxNumberRooms",     default: ""    },
    { selector: "listingType",        default: "ANY" },
];

const priceOrderingAscending = document.getElementById("orderingAscending");
const priceOrderingDescending = document.getElementById("orderingDescending");
const applyFiltersBtn = document.getElementById("applyFiltersBtn");
const propertyContainer = document.getElementById("propertyContainer");

// onclick handlers
applyFiltersBtn.addEventListener("click", (elem, ev) => {
    let filterRequestUrl = CLIENT_BASE + "/";

    let paramSeparator = "?";
    for (let filter of filters) {
        const value = document.getElementById(filter.selector).value;
        if (value !== filter.default) { 
            filterRequestUrl += paramSeparator + filter.selector + "=" + value;
            paramSeparator = "&";
        }
    }

    const priceOrderingSelected = document.querySelector("input[name='orderingTypes']:checked");
    const priceOrdering = priceOrderingSelected ? priceOrderingSelected.value : "ASC";
    if (priceOrdering === "ASC" || priceOrdering === "DESC") {
        filterRequestUrl += paramSeparator + "priceOrdering=" + priceOrdering;
    }
    
    window.location.assign(filterRequestUrl);
});

/* Check GET request parameters to change filter values and content */
const requestParams = new URL(window.location.toLocaleString()).searchParams;
let propertyRequest = SERVER_BASE + "/property";

let filterSeparator = "?";
for (let filter of filters) {
    if (requestParams.has(filter.selector)) {
        const filterValue = requestParams.get(filter.selector);

        if (filterValue !== filter.default) {
            propertyRequest += filterSeparator + filter.selector + "=" + filterValue;
            document.getElementById(filter.selector).value = filterValue;
        }
        filterSeparator = "&";
    }
}

if (requestParams.has("priceOrdering")) {
    const priceOrdering = requestParams.get("priceOrdering");
    const priceOrderingOption = (priceOrdering === "DESC") ? priceOrderingDescending : priceOrderingAscending;
    priceOrderingOption.click();
    propertyRequest += filterSeparator + "priceOrdering=" + priceOrdering;
} else {
    priceOrderingAscending.click();
}

// Get and display properties according to the filters applied
fetch(propertyRequest, { credentials: 'include' })
.then(response => response.json())
.then(properties => {
    // Display property elements
    for (let prop of properties) {
        createPropertyElement(prop)
    }
});
