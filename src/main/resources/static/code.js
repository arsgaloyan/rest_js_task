$(async function () {
    await getAuthAdmin();
    await getAllUsers();
    await newUser();
    removeUser();
    updateUser();

});

async function getAuthAdmin() {
    fetch("/api/admin/user")
        .then(response => response.json())
        .then(data => {
            let role = data.roles[0].role.substring(5);
            let user = `$(
            <tr class="fs-5">
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.surname}</td>
                <td>${data.username}</td>
                <td>${data.email}</td>
                <td>${role}</td>)`;
            $('#userTableUserView').append(user);
        })
        .catch(error => console.log(error))
}

// При переключении на вкладку User скрываем контейнер userTableAdminView
$('#pills-profile-tab').on('click', function() {
    $('.admin-view').hide();
  });
  
  // При переключении на вкладку Admin показываем контейнер userTableAdminView
  $('#pills-home-tab').on('click', function() {
    $('.admin-view').show();
  });

// Получение списка пользователей 
async function getAllUsers() {

    const url = '/api/admin'
    const container = document.querySelector('tbody')
    let resultData = ''

    const dataShow = (elements) => {
        elements.forEach(element => {

            const rolesName = element.roles.map(role => role.role.replace('ROLE_', ' ')).join(', ')
            resultData += `
    <tr>
        <td>${element.id}</td>
        <td>${element.name}</td>
        <td>${element.surname}</td>
        <td>${element.username}</td>
        <td>${element.email}</td>
        <td>${rolesName}</td>
        <td>
            <a id="btnEdit" class="btnEdit btn btn-primary" data-userid="${element.id}" data-action="Edit" data-bs-toggle="modal" data-bs-target="#editUserModal">Edit</a>
            </td>
        <td>
            <a id="btnDel" class="btnDelete btn btn-danger" data-userid="${element.id}" data-action="Delete" data-bs-toggle="modal" data-bs-target="#delUserModal">Delete</a>
            
            </td>
    </tr>`
        });
        container.innerHTML = resultData
    }

    fetch(url)
        .then(response => response.json())
        .then(data => dataShow(data))
        .catch(error => console.log(error))

}

async function getUser(id) {
    let url = "/api/admin/" + id;
    let response = await fetch(url);
    return await response.json();
}

async function getRolesOption() {
    await fetch("/api/admin/roles")
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.value = role.id;
                el.text = role.role.substring(5);
                $('#rolesNew')[0].appendChild(el);
            })
        })
}

// Создание нового пользователя
async function newUser() {
    await getRolesOption();

    const createForm = document.forms["createForm"];
    const createLink = document.querySelector('#addNewUser');
    const createButton = document.querySelector('#createUserButton');

    // Добавляем обработчик событий на нажатие ссылки
    createLink.addEventListener('click', (event) => {
        event.preventDefault();
        console.log("  нажал на кнопку")
        createForm.style.display = 'block';
    });
    createForm.addEventListener('submit', addNewUser)
    createButton.addEventListener('click', addNewUser);

    async function addNewUser(e) {
        e.preventDefault();
        let newUserRoles = [];
        for (let i = 0; i < createForm.role.options.length; i++) {
            if (createForm.role.options[i].selected) newUserRoles.push({
                id: createForm.role.options[i].value,
                roles: createForm.role.options[i].text
            })
        }

        fetch("/api/admin/new", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: createForm.usernameNew.value,
                name: createForm.nameNew.value,
                surname: createForm.surnameNew.value,
                email: createForm.emailNew.value,
                password: createForm.passwordNew.value,
                roles: newUserRoles
            })
        }).then(() => {
            console.log("создан пользователь: ");
            createForm.reset();
            $('#addFormCloseButton').click();
            getAllUsers();
        })
    }
}

function removeUser() {

    const deleteForm = document.forms["deleteForm"];
    const id = deleteForm.idDel.value;
    const hrefDel = `/api/admin/delete/${id}`;

    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch(hrefDel, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            console.log("пользователь удален", id)
            getAllUsers();
            $('#deleteFormCloseButton').click();

        })
    })
}

$('#delUserModal').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('userid');
    showDeleteModal(id);
})
//

$('#deleteUserButton').click(() => {

    removeUser();
});


async function showDeleteModal(id) {
    console.log(" появилась форма удаления")
    let user = await getUser(id)
    const form = document.forms["deleteForm"];

    form.idDel.value = user.id;
    form.usernameDel.value = user.username;
    form.nameDel.value = user.name;
    form.surnameDel.value = user.surname;
    form.emailDel.value = user.email;
    form.passwordDel.value = user.password;

    $('#rolesDel').empty();

    user.roles.forEach(role => {
        let el = document.createElement("option");
        el.text = role.role.substring(5);
        el.value = role.id;
        $('#rolesDel')[0].appendChild(el);

    });

}


function updateUser() {

    const editForm = document.forms["editForm"];
    const id = editForm.idEdit.value;
    const hrefEdit = `/api/admin/edit/${id}`;

    editForm.addEventListener("submit", async (ev) => {
        ev.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.rolesEdit.options.length; i++) {
            if (editForm.rolesEdit.options[i].selected) editUserRoles.push({
                id: editForm.rolesEdit.options[i].value,
                role: editForm.rolesEdit.options[i].text
            })
        }

        fetch(hrefEdit, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id: id,
                username: editForm.usernameEdit.value,
                name: editForm.nameEdit.value,
                surname: editForm.surnameEdit.value,
                email: editForm.emailEdit.value,
                password: editForm.passwordEdit.value,
                roles: editUserRoles,
            }),
        })
            .then(() => {
                console.log("пользователь изменен", id);
                getAllUsers();
                $('#editFormCloseButton').click();
            });
    });
}

$('#editUserModal').on('show.bs.modal', (ev) => {
    let button = $(ev.relatedTarget);
    let id = button.data('userid');
    showEditModal(id);
});

$('#editUserButton').click(() => {
    updateUser();
});

async function showEditModal(id) {

    console.log("появилась форма редактирования");
    let user = await getUser(id);
    const form = document.forms["editForm"];

    form.idEdit.value = user.id;
    form.usernameEdit.value = user.username;
    form.nameEdit.value = user.name;
    form.surnameEdit.value = user.surname;
    form.emailEdit.value = user.email;
    form.passwordEdit.value = user.password;

    $('#rolesEdit').empty();
    await fetch("/api/admin/roles")
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.value = role.id;
                el.text = role.role.substring(5);
                $('#rolesEdit')[0].appendChild(el);
            })
        })
}
  

  