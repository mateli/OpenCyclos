function renderVirtualKeyboard(field) {
	if (useVirtualKeyboard) {
		field = $(field || 'cyclosPassword');
		Element.addClassName(field, "InputBoxDisabled");
		field.readOnly = true;
		field.value = "";
		document.write('<div id="virtualKeyboard" style="width:330px"></div>');
		var options = {
			'full': fullLabel, 
			'submitLabel': submitLabel, 
			'capsLock': capsLockLabel, 
			'contrast': contrastLabel, 
			'clear': clearLabel
		};
		if (numericPassword) {
			options.chars = shuffle("0123456789".split("")).join("");
			options.showFull = true;
		}
		new VirtualKeyboard($('virtualKeyboard'), field, options);
	}
}

function publicRegisterUser() {
	self.location = context + "/do/publicCreateMember" + (groupFilterId != null ? "?groupFilterId=" + groupFilterId : "");
}

function createInput(type, id, name) {
	var input = document.createElement("input");
	input.setAttribute("type", type);
	if (id) {
		input.setAttribute("id", id);
	}
	if (name) {
		input.setAttribute("name", name);
	}
	return input;
}

var loginBehaviour = {
	'#cyclosLogin': function(form) {
		form.action = context + loginAction;
		form.method = "post";

		//For legacy customized login.jsp, there was an input id=cyclosMember but not with name 'member'
		var member = $('cyclosMember');
		if (member && member.name != 'member') {
			member.setAttribute('name', 'member');
		}
		//Same for 'principal'
		var username = $('cyclosUsername');
		if (username && username.name != 'principal') {
			username.setAttribute('name', 'principal');
		}

		//Same for 'password'
        var password = $('cyclosPassword');
        if (password && password.name != 'password') {
            password.setAttribute('name', 'password');
        }

		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
    
    '#cyclosMember': function(input) {
        Event.observe(input, "keypress", function(event) {
            if (typedCode(event) == Event.KEY_RETURN) {
                Event.stop(event);
                setFocus('cyclosUsername');
            }           
        });
    },
    
    '#cyclosUsername': function(input) {
        Event.observe(input, "keypress", function(event) {
            if (typedCode(event) == Event.KEY_RETURN) {
                Event.stop(event);
                var password = $('cyclosPassword');
                if (!password.disabled && !password.readonly) {
                    setFocus(password);
                }
            }           
        });
    },
	
	'#accessOptionsLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			var accessOptions = $('accessOptions');
			var options = {duration: 0.2};
			if (accessOptions.visible()) {
				new Effect.BlindUp(accessOptions, options);
			} else {
				new Effect.BlindDown(accessOptions, options);
			} 
		}
	}
};
Behaviour.register(loginBehaviour);
 
function ensureLoginForm() {
	if ($('cyclosLogin')) {
		return;
	}
	var form = document.createElement("form");
	form.setAttribute("id", "cyclosLogin");
	var message = "You forgot to include for login form to the static file login.jsp.<br>Please, add the following line to it:<br>&lt;jsp:include flush=\"true\" page=\"/pages/access/includes/loginForm.jsp\" /&gt;<br>This fallback login form have been automatically added.<br><br>";
	var formHTML = "User: <input id='cyclosUsername'><br>Password: <input type='password' id='cyclosPassword'><br><input type='submit' value='Submit'>";
	form.innerHTML = "<div align='center'><div style='width:600px; background-color:white; padding:10px; font-size: 14px; border:2px solid red; margin:auto;' align='left'>" + message + formHTML + "</div></div>";
	document.body.appendChild(form);
	loginBehaviour["#cyclosLogin"](form);
}

enableMessageDiv = false;
Event.observe(self, "load", function() {
	try {
		var focusField = ($('cyclosMember') || $('cyclosUsername'));
		if (focusField.disabled || focusField.readOnly) {
			focusField = $('cyclosPassword');
		}
		setFocus(focusField);
	} catch (e) {}
});