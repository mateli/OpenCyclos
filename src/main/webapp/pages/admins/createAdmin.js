function setPostAction(action) {
	var postAction = this.elements["postAction"];
	if (postAction == null) {
		//Create the hidden if not found
		postAction = document.createElement("input");
		postAction.setAttribute("type", "hidden");
		postAction.setAttribute("name", "postAction");
		this.appendChild(postAction);
	}
	postAction.value = action;
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#saveAndNewButton': function(button) {
		button.onclick = setPostAction.bind(button.form, 'new');
	},
	
	'#saveAndOpenProfileButton': function(button) {
		button.onclick = setPostAction.bind(button.form, 'openProfile');
	}
});

Event.observe(self, "load", function() {
	getObject("admin(user).username").focus();
})