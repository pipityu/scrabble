let selectedChar;
$(function() {
    $("#generateChars").on("click", function () {
        $("#generateChars").prop("disabled", true);
        $.getJSON('http://localhost:8080/generate', { get_param: 'name' }, function(data) {
            $.each(data, function(index, element) {
                //alert(index);
                $("#ch"+(index+1)).html(element.name);
            });
        });
    });

    //Check available characters all the time when select changes
    //take the selected character into a variable for further usage
    $("#availableCharacters").on("change", function () {
        checkCharacters();
    });


//Set fields consider of the selected character!
    $(".field").on("click",function(event){
        let fieldId = event.target.id;
        if(selectedChar !== $("#"+fieldId).text()){
            $("#"+fieldId).text(selectedChar);
            $.ajax({url: "http://localhost:8080/changetoken", type: "PUT", data: {charName: selectedChar, id: fieldId}, success: checkCharacters
                });
        }

    });

    $("#newGame").on("click",function(event){
            $.ajax({url: "http://localhost:8080/newgame", type: "PUT", success: function () {
                    checkCharacters();
                    $(".field").text("-");
                    $(".myChar").text("-");
                    $("#generateChars").prop("disabled", false);
                }
            });
    });

    let hints;
    $("#hintsButton").on("click",function(event){
        hints = "";
        $.getJSON('http://localhost:8080/hints', function(data) {
            //let keys = Object.keys(data);
            $.each(data, function (index, element) {
                hints+= index+":"+element+" ";
            });
            $('#hints').text(hints);
        });
    });
});


//check available characters
//error: when run out of characters the selected still contain one letter!!
function checkCharacters(){
    selectedChar = $('#availableCharacters option:selected').text();
    $("#availableCharacters option").remove();
    $.getJSON('http://localhost:8080/remaintokens', { get_param: 'name' }, function(data) {
        $.each(data, function (index, element) {
            $('#availableCharacters').append(new Option(element.name, element.name));
        });
        $("#availableCharacters option[value=" + selectedChar+"]").attr('selected', 'selected');
    });
}
