package ua.training.model.utils.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TagHandler extends TagSupport {

    private String name;
    private String surname;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int doStartTag() {
        JspWriter out=pageContext.getOut();
        try{
            out.print(name + " " + surname);
        }catch(Exception e){System.err.println(e);}
        return SKIP_BODY;
    }

}
