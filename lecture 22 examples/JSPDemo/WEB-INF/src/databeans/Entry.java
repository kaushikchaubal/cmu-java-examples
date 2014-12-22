package databeans;

import org.mybeans.nonmodifiable.NMDate;
import org.mybeans.nonmodifiable.NMSQLDate;

public class Entry {
	private int id;

    private String    additional;
    private String    address;
    private NMSQLDate birthday;
    private String    cellPhone;
    private String    city;
    private String    country;
    private String    email;
    private String    fax;
    private String    firstNames;
    private String    homePhone;
    private String    lastName;
    private String    receivedCards;
    private String    sentCards;
    private NMSQLDate spouseBirthday;
    private String    spouseCell;
    private String    spouseEmail;
    private String    spouseFirst;
    private String    spouseLast;
    private String    spouseWork;
    private String    state;
    private NMDate    updated;
    private String    workPhone;
    private String    zip;

    public Entry(int id) { this.id = id; }

    public String getAdditional()       { return additional;     }
    public String getAddress()          { return address;        }
    public NMSQLDate getBirthday()      { return birthday;       }
    public String getCellPhone()        { return cellPhone;      }
    public String getCity()             { return city;           }
    public String getCountry()          { return country;        }
    public String getEmail()            { return email;          }
    public String getFax()              { return fax;            }
    public String getFirstNames()       { return firstNames;     }
    public String getHomePhone()        { return homePhone;      }
    public int    getId()               { return id;             }
    public String getLastName()         { return lastName;       }
    public String getReceivedCards()    { return receivedCards;  }
    public String getSentCards()        { return sentCards;      }
    public NMSQLDate getSpouseBirthday() { return spouseBirthday; }
    public String getSpouseCell()       { return spouseCell;     }
    public String getSpouseEmail()      { return spouseEmail;    }
    public String getSpouseFirst()      { return spouseFirst;    }
    public String getSpouseLast()       { return spouseLast;     }
    public String getSpouseWork()       { return spouseWork;     }
    public String getState()            { return state;          }
    public NMDate getUpdated()          { return updated;        }
    public String getWorkPhone()        { return workPhone;      }
    public String getZip()              { return zip;            }

    public void setAdditional(String s)     { additional     = s; }
    public void setAddress(String s)        { address        = s; }
    public void setBirthday(NMSQLDate d)    { birthday       = d; }
    public void setCellPhone(String s)      { cellPhone      = s; }
    public void setCity(String s)           { city           = s; }
    public void setCountry(String s)        { country        = s; }
    public void setEmail(String s)          { email          = s; }
    public void setFax(String s)            { fax            = s; }
    public void setFirstNames(String s)     { firstNames     = s; }
    public void setHomePhone(String s)      { homePhone      = s; }
    public void setLastName(String s)       { lastName       = s; }
    public void setReceivedCards(String s)  { receivedCards  = s; }
    public void setSentCards(String s)      { sentCards      = s; }
    public void setSpouseBirthday(NMSQLDate d) { spouseBirthday = d; }
    public void setSpouseCell(String s)     { spouseCell     = s; }
    public void setSpouseEmail(String s)    { spouseEmail    = s; }
    public void setSpouseFirst(String s)    { spouseFirst    = s; }
    public void setSpouseLast(String s)     { spouseLast     = s; }
    public void setSpouseWork(String s)     { spouseWork     = s; }
    public void setState(String s)          { state          = s; }
    public void setUpdated(NMDate d)        { updated        = d; }
    public void setWorkPhone(String s)      { workPhone      = s; }
    public void setZip(String s)            { zip            = s; }
}
