namespace FDMTermProject.Library
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text;
    using System.Xml;
    using System.Xml.Serialization;

    /// <summary>
    ///  XML serializer class
    /// </summary>
    public static class SerializeHelper
    {
        /// <summary>
        /// Creates an object from an XML serialization.
        /// </summary>
        /// <param name="xmlObj">The XML to create the object from.</param>
        /// <param name="objType">The type of opject to put the xml mfvvalues into.</param>
        /// <returns>The object containing the mfvvalues in the XML.</returns>
        public static object DeserializeObj(string xmlObj, Type objType)
        {
            System.IO.MemoryStream memoryStream = null;
            try
            {
                XmlSerializer s = new XmlSerializer(objType);
                memoryStream = new System.IO.MemoryStream(StringToUTF8ByteArray(xmlObj));
                object deserializedObject = s.Deserialize(memoryStream);
                return deserializedObject;
            }
            catch
            {
                return null;
            }
            finally
            {
                if (memoryStream != null)
                {
                    memoryStream.Dispose();
                }
            }
        }

        /// <summary>
        /// Put the mfvvalues of an object into an XML layout.
        /// </summary>
        /// <param name="serialize">Object to convert to store as XML.</param>
        /// <param name="objectType">Type of the previous object.</param>
        /// <returns>The object stored as XML.</returns>
        public static string SerializeObject(object serialize, Type objectType)
        {
            MemoryStream memoryStream = null;
            try
            {
                string xmlizedString = null;
                memoryStream = new MemoryStream();
                XmlSerializer xs = new XmlSerializer(objectType);

                XmlSerializerNamespaces xmlNamespace = new XmlSerializerNamespaces();
                xmlNamespace.Add(string.Empty, string.Empty);

                xs.Serialize(memoryStream, serialize, xmlNamespace);
                xmlizedString = UTF8ByteArrayToString(memoryStream.ToArray());
                return xmlizedString;
            }
            catch (Exception ex)
            {
                return ex.Message;
            }
            finally
            {
                if (memoryStream != null)
                {
                    memoryStream.Dispose();
                }
            }
        }

        /// <summary>
        /// Converts a string to a byte array.
        /// </summary>
        /// <param name="xmlString">String value to convert.</param>
        /// <returns>A byte array of the string.</returns>
        private static byte[] StringToUTF8ByteArray(string xmlString)
        {
            UTF8Encoding encoding = new UTF8Encoding();
            byte[] byteArray = encoding.GetBytes(xmlString);
            return byteArray;
        }

        /// <summary>
        /// Converts a byte array to a string.
        /// </summary>
        /// <param name="characters">Byte array to convert to string.</param>
        /// <returns>String version of the byte array.</returns>
        private static string UTF8ByteArrayToString(byte[] characters)
        {
            UTF8Encoding encoding = new UTF8Encoding();
            string constructedString = encoding.GetString(characters);
            return constructedString;
        }
    }
}
