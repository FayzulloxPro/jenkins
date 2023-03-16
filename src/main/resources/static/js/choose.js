
// let element = document.getElementById("new-line");

function choose(id, role) {
    document.getElementById("role").value = role;
    document.getElementById("id").value = id;
}

let element = document.getElementById("v-activated");

function changeActivatedHelperFunc(activated_id) {
    console.log(activated_id);
    document.getElementById("updated").value = activated_id;
}


function changeDeletedHelper(deleted_id) {
    document.getElementById("deleted_id").value = deleted_id;
}

