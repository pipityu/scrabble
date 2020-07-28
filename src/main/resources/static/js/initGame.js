let availableCharacters;
let id;
let fields;
$(function() {
    initGame();
});

function initGame(){
    //Create  fields with ids
    for(let i=1; i<=15; i++){
        for(let j=1; j<=15; j++){
            id = i+"-"+j;

            fields = document.createElement("div");
            fields.className = "field";
            fields.id = id;
            fields.innerText = "-";
            document.getElementById("gameBoard").appendChild(fields);
        }
    }

    //Check for the first time of the available characters
    availableCharacters = document.createElement("select");
    availableCharacters.id = "availableCharacters";
    availableCharacters.innerHTML =
        "<option selected>Choose</option>";
    document.getElementById("availableCharactersDiv").appendChild(availableCharacters);
    $.getJSON('http://localhost:8080/remaintokens', { get_param: 'name' }, function(data) {
        $.each(data, function (index, element) {
            $('#availableCharacters').append(new Option(element.name, element.name));
        });
    });
}