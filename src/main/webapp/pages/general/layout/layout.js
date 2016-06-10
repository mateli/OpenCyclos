
function observeMenu(className, hoverClassName) {
	var link = this.getAttribute("linkURL");
	if (!isEmpty(link)) {
		Event.observe(this, "click", function (ev) {
			var confirmationMessage = this.getAttribute("confirmationMessage");
			if (!isEmpty(confirmationMessage)) {
				if (!confirm(confirmationMessage)) {
					return;
				}
			}
			self.location = context + link;
			Event.stop(ev);
		}.bindAsEventListener(this));
	}
	changeClassOnHover(this, className, hoverClassName, isLeftMenu);
}

var currentPageName = "query(pageParameters).currentPage";

var menuIndex = -1;
var menuCount = 0;
var layoutBehaviour = {
		
	'#loginDataBar': function(el) {
		if (self !== top) {
			el.addClassName('loginDataBarNoTop');
		}
	},
	
	'#menuHolder': function(el) {
		isLeftMenu = ('' + el.getStyle('float')).toLowerCase() == 'left';
		if (isLeftMenu) {
			// We don't want open menus on top
			restoreMenu();
		}
	},

	'.menu': function(div) {
		var menu = div;
		menu.index = allMenus.length;
		menuIndex++;
		setPointer(menu);
		var className;
		var hoverClassName;
		switch (menuIndex) {
			case 0:
				className = "firstMenu";
				hoverClassName = "firstMenuHover";
				Element.addClassName(menu, className);
				break;
			case menuCount - 1:
				className = "lastMenu";
				hoverClassName = "lastMenuHover";
				Element.addClassName(menu, className);
				break;
			default:
				className = "menu";
				hoverClassName = "menuHover";
				break;
		}
		observeMenu.apply(menu, [className, hoverClassName]);
		// There is no effect for admins
		var useEffect = !isAdmin && isLeftMenu;
		
		Event.observe(menu, "click", toggleSubMenu.bind(self, menu, useEffect));
		
		if (!isLeftMenu && isEmpty(menu.getAttribute("confirmationMessage")) && !is.ie) {
			var sub = getSubMenuContainer(menu);
			if (sub) {
				Event.observe(sub, "mouseover", function() {
					menu.hoveringOver = sub;
				});
				Event.observe(sub, "mouseout", function() {
					if (menu.hoveringOver === sub) {
						menu.hoveringOver = null;
					}
				});
				Selector.findChildElements(menu, [".menuText"]).each(function(el) {
					Event.observe(el, "mouseover", function() {menu.hoveringOver = el;});
					Event.observe(el, "mouseout", function() {if (menu.hoveringOver === el) menu.hoveringOver = null;});
				});
				Event.observe(menu, "mouseover", function() {
					menu.hoveringOver = menu;
					openSubMenu(menu, useEffect);
				});
				Event.observe(menu, "mouseout", function() {
					if (menu.hoveringOver == menu) {
						menu.hoveringOver = null;
					}
					self.setTimeout(function() {
						if (menu.hoveringOver == null) {
							closeSubMenu(menu, useEffect);
						}
					}, 10);
				});
			}
		}
	},	

	'.subMenu': function(div) {
		//Change the class name for submenus
		setPointer(div);
		observeMenu.apply(div, ["subMenu", "subMenuHover"]);
	},

	'.footerNote': function(div) {
		var title = div.getAttribute("title") || resultsTitle;
		var helpPage = div.getAttribute("helpPage");
		var helpId = isEmpty(helpPage) ? null : "help_" + new Date().getTime() + Math.random();
		var help = isEmpty(helpPage) ? "&nbsp;" : "<img class='help' id='" + helpId + "' helpPage='" + helpPage + "' src='" + context + "/pages/images/help.gif'>";
		//Transform the footer notes into a window
		sb = new StringBuffer(20)
			.append("<table class=\"defaultTableContent\" cellspacing=\"0\" cellpadding=\"0\">")
				.append("<tr>")
					.append("<td class=\"tdHeaderTable\">").append(title).append("</td>")
					.append("<td class=\"tdHelpIcon\" align='right'>").append(help).append("</td>")
				.append("</tr>")
				.append("<tr>")
					.append("<td colspan=\"2\" class=\"innerBorder tdContentTableForms\" align=\"center\">")
					.append("<br>")
					.append(div.innerHTML)
					.append("<br>&nbsp;")
					.append("</td>")
				.append("</tr>")
				.append("<tr>")
					.append("<td class=\"bottomLeft\"></td>")
					.append("<td class=\"bottomRight\"></td>")
				.append("</tr>")
			.append("</table>");
	    Element.replace(div, sb.toString());
	    //Apply the normal help behaviour to the generated help icon
	    if (helpId) {
	    	headBehaviour['img']($(helpId));
	    }
	},
	
	'tr': function(tr) {
		if (Element.hasClassName(tr, 'ClassColor1')) {
			changeClassOnHover(tr, "ClassColor1", "ClassColorSelected");
		} else if (Element.hasClassName(tr, 'ClassColor2')) {
			changeClassOnHover(tr, "ClassColor2", "ClassColorSelected");
		}
	},
	
	'a': function(a) {
		a = $(a);
		var isMemberProfile = a.hasClassName("profileLink");
		var isAdminProfile = !isMemberProfile && a.hasClassName("adminProfileLink");
		var isOperatorProfile = !isMemberProfile && !isAdminProfile && a.hasClassName("operatorProfileLink");
		if (isMemberProfile || isAdminProfile || isOperatorProfile) {
			setPointer(a);
			var path = isMemberProfile ? 'profile' : isAdminProfile ? 'adminProfile' : 'operatorProfile';
			var param = isMemberProfile ? 'memberId' : isAdminProfile ? 'adminId' : 'operatorId';
			a.onclick = function() {
				self.location = pathPrefix + "/" + path + "?" + param + "=" + a.getAttribute(param);
			}
		} else if (a.hasClassName("paginationLink")) {
			setPointer(a);
			a.onclick = function(event) {
				var onClickHandler = a.getAttribute("onClickHandler");
				var pageNumber = parseInt(a.getAttribute("jumpToPage"), 10);
				
				if (isEmpty(onClickHandler)) {
					// The default action, when no onClickHandler is given, is to change the currentPageName element and submit the form 
					var formName = a.getAttribute("form");
					var form = isEmpty(formName) ? document.forms[0] : document.forms[formName];
					var currentPage = form.elements[currentPageName];
					if (currentPage == null) {
						currentPage = document.createElement("input");
						currentPage.setAttribute("type", "hidden");
						currentPage.setAttribute("name", currentPageName);
						currentPage = form.appendChild(currentPage);
					}
					currentPage.value = pageNumber;
					form.submit();
					Element.remove(currentPage);
				} else {
					//When a custom action is given, perform the custom action 
					eval(onClickHandler + "(" + pageNumber + ")");
				}
			};
		}
	}
};
Behaviour.register(layoutBehaviour);
showMessageDiv();
Event.observe(self, "load", hideMessageDiv);
