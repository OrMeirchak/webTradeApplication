
// extract the context path using the window.location data items
function calculateContextPath() {
    var pathWithoutLeadingSlash = window.location.pathname.substring(1);
    var contextPathEndIndex = pathWithoutLeadingSlash.indexOf('/');
    var returnValue= pathWithoutLeadingSlash.substr(0, contextPathEndIndex);

    return returnValue;
}

// returns a function that holds within her closure the context path.
// the returned function is one that accepts a resource to fetch,
// and returns a new resource with the context path at its prefix
function wrapBuildingURLWithContextPath() {
    var contextPath = calculateContextPath();
    return function(resource) {
     var returnValue=( "/" + contextPath + "/" + resource);
    console.log("return value = "+returnValue);
         return returnValue;
    };
}

// call the wrapper method and expose a final method to be used to build complete resource names (buildUrlWithContextPath)
var buildUrlWithContextPath = wrapBuildingURLWithContextPath(); 