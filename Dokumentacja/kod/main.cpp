#include <iostream>
using namespace std;

class Element 
{
public:
int wartosc;
Element* nastepny;
Element* poprzedni;

Element(int val) : wartosc(val), nastepny(nullptr), poprzedni(nullptr) {}
    
};

class DwukierunkowaLista 
{
private:
   Element* poczatek;
   Element* koniec;

public:
    DwukierunkowaLista() : poczatek(nullptr), koniec(nullptr) {}
    
    void dodaj_na_poczatek(int val)
    {
        Element* nowy_element = new Element(val);

        if (poczatek == nullptr)
        {
            poczatek = koniec = nowy_element;
        }
        else 
        {
            nowy_element->nastepny = poczatek;
            poczatek->poprzedni = nowy_element;
            poczatek = nowy_element;
        }
    }

    void dodaj_na_koniec(int val)
    {
        Element* nowy_element = new Element(val);

        if (koniec == nullptr)
        {
            poczatek = koniec = nowy_element;
        }
        else
        {
            nowy_element->poprzedni = koniec;
            koniec->nastepny = nowy_element;
            koniec = nowy_element;
        }
    }

    void dodaj_na_indeks(int val, int indeks)
    {
        Element* nowy_element = new Element(val);

        if (indeks = 0)
        {
            dodaj_na_poczatek(val);
            return;
        }
        
        Element* aktualny = poczatek;
        for (int i = 0; i < indeks -1; i++)
        {
            if (aktualny == nullptr)
            {
                cout << "Podany indeks jest spoza listy."<<endl;
                delete nowy_element;
                return;
            }
            aktualny = aktualny->nastepny;
        }
        
        nowy_element->nastepny = aktualny->nastepny;
        nowy_element->poprzedni = aktualny;
        if (aktualny->nastepny != nullptr)
        {
            aktualny->nastepny->poprzedni = nowy_element;
        }
        else
        {
            koniec = nowy_element;
        }
        aktualny->nastepny = nowy_element;
    }

    void usun_z_poczatku()
    {
        if (poczatek == nullptr)
        {
            cout << "Lista jest pusta."<< endl;
            return;
        }
        Element* tmp = poczatek;
        if (poczatek->nastepny != nullptr)
        {
            poczatek->nastepny->poprzedni = nullptr;
            poczatek = poczatek->nastepny;
        }
        else
        {
            poczatek = koniec = nullptr;
        }
        delete tmp;
    }

    void usun_z_konca()
    {
        if (koniec == nullptr) 
        {
            cout << "Lista jest pusta." << endl;
            return;
        }

        Element* tmp = koniec;
        if (koniec->poprzedni != nullptr) 
        {
            koniec->poprzedni->nastepny = nullptr;
            koniec = koniec->poprzedni;
        } 
        else 
        {
            poczatek = koniec = nullptr;
        }
        delete tmp;
    }

    void usun_z_indeksu(int indeks) 
    {
        if (poczatek == nullptr) 
        {
            cout << "Lista jest pusta." << endl;
            return;
        }

        if (indeks == 0) 
        {
            usun_z_poczatku();
            return;
        }

        Element* aktualny = poczatek;
        for (int i = 0; i < indeks; i++) 
        {
            if (aktualny == nullptr) 
            {
                cout << "Podany indeks jest poza zakresem listy." << endl;
                return;
            }
            aktualny = aktualny->nastepny;
        }

        aktualny->poprzedni->nastepny = aktualny->nastepny;
        if (aktualny->nastepny != nullptr) 
        {
            aktualny->nastepny->poprzedni = aktualny->poprzedni;
        } 
        else 
        {
            koniec = aktualny->poprzedni;
        }
        delete aktualny;
    }

    void wyswietl()
    {
        Element* aktualny = poczatek;
        while (aktualny != nullptr)
        {
            cout << aktualny->wartosc <<" ";
            aktualny = aktualny->nastepny;
        }
        cout << endl;
    }

    void wyswietl_odwrotnie() 
    {
        Element* aktualny = koniec;
        while (aktualny != nullptr) 
        {
            cout << aktualny->wartosc << " ";
            aktualny = aktualny->poprzedni;
        }
        cout << endl;
    }

    void wyswietl_nastepny(Element* el) 
    {
        if (el != nullptr && el->nastepny != nullptr) 
        {
            cout << "Nastepny element: " << el->nastepny->wartosc << endl;
        } 
        else 
        {
            cout << "Brak nastepnego elementu." << endl;
        }
    }

    void wyswietl_poprzedni(Element* el) 
    {
        if (el != nullptr && el->poprzedni != nullptr) 
        {
            cout << "Poprzedni element: " << el->poprzedni->wartosc << endl;
        } 
        else 
        {
            cout << "Brak poprzedniego elementu." << endl;
        }
    }

    void czysc() 
    {
        while (poczatek != nullptr) 
        {
            Element* tmp = poczatek;
            poczatek = poczatek->nastepny;
            delete tmp;
        }
        koniec = nullptr;
    }

    ~DwukierunkowaLista()
    {
        while (poczatek != nullptr)
        {
            Element* tmp = poczatek;
            poczatek = poczatek->nastepny;
            delete tmp;
        }
        koniec = nullptr;
    }
};

int main() 
{
    DwukierunkowaLista lista;
    lista.dodaj_na_poczatek(1);
    lista.dodaj_na_poczatek(2);
    lista.dodaj_na_koniec(3);
    lista.dodaj_na_indeks(4, 1);
    lista.wyswietl_odwrotnie();
    lista.czysc(); 


    return 0;
}